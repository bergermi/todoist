package repositories

import java.time.Instant
import java.util.UUID

import domain.{CreateTodo, Todo}
import scalikejdbc._
import shared.Schema

class TodoRepository extends SQLSyntaxSupport[Todo] {
  override def tableName: String = s"${Schema.Todoist}.todo"
  private val t                  = this.syntax("t")

  def create(createTodo: CreateTodo)(implicit session: DBSession = autoSession): Todo = {
    val todo = Todo(
      id = UUID.randomUUID().toString,
      description = createTodo.description,
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

  def get(id: String)(implicit session: DBSession = ReadOnlyAutoSession): Option[Todo] = {
    withSQL {
      select
        .from(this as t)
        .where
        .eq(t.id, id)
    }.map(this(t)).single.apply()
  }

  def update(id: String, createTodo: CreateTodo)(implicit session: DBSession = autoSession): Int = {
    withSQL {
      scalikejdbc
        .update(this as t)
        .set(
          column.description -> createTodo.description
        )
        .where
        .eq(t.id, id)
    }.update.apply()
  }

  def delete(id: String)(implicit session: DBSession = autoSession): Int = {
    withSQL {
      scalikejdbc.delete
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
