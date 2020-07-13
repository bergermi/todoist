package repositories

import java.time.Instant
import java.util.UUID

import domain.{Todo, TodoContent}
import scalikejdbc._
import shared.Schema

class TodoRepository extends SQLSyntaxSupport[Todo] {
  override def tableName: String = s"${Schema.Todoist}.todo"
  private val t                  = this.syntax("t")

  def create(todoContent: TodoContent)(implicit session: DBSession = autoSession): Todo = {
    val todo = Todo(
      id = UUID.randomUUID().toString,
      description = todoContent.description,
      createdAt = Some(Instant.now)
    )

    withSQL {
      insert
        .into(this)
        .namedValues(
          column.id          -> todo.id,
          column.description -> todo.description,
          column.createdAt   -> todo.createdAt
        )
    }.update().apply()

    todo
  }

  def getAll(implicit session: DBSession = ReadOnlyAutoSession): Seq[Todo] = {
    withSQL {
      select
        .from(this as t)
    }.map(this(t)).list.apply()
  }

  def get(id: String)(implicit session: DBSession = ReadOnlyAutoSession): Option[Todo] = { // TODO implicit?
    withSQL {
      select
        .from(this as t)
        .where // TODO what is 'this' here?
        .eq(t.id, id)
    }.map(this(t)).single.apply() // TODO 'this(t)' argument?

    // TODO handle exception or use 'first' instead of 'single'?
  }

  def update(todo: Todo)(implicit session: DBSession = autoSession): Int = {
    val createdAt = Instant.now

    withSQL {
      scalikejdbc
        .update(this as t)
        .set(
          column.description -> todo.description,
        )
        .where
        .eq(t.id, todo.id)
    }.update.apply()
  }

  def delete(id: String)(implicit session: DBSession = autoSession): Int = { // TODO what are the different session types?
    withSQL {
      scalikejdbc.delete // TODO or user deleteFrom?
        .from(this as t)
        .where
        .eq(t.id, id)
    }.update.apply()
  }

  def opt(s: SyntaxProvider[Todo])(rs: WrappedResultSet): Option[Todo] =
    rs.stringOpt(s.resultName.id).map(_ => apply(s.resultName)(rs))

  def apply(s: SyntaxProvider[Todo])(rs: WrappedResultSet): Todo = apply(s.resultName)(rs)

  def apply(rn: ResultName[Todo])(rs: WrappedResultSet): Todo =
    Todo(
      id = rs.get[String](rn.id),
      description = rs.get[String](rn.description),
      createdAt = rs.offsetDateTimeOpt(rn.createdAt).map(_.toInstant)
    )
}
