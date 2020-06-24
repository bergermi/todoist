package repositories

import java.time.Instant
import java.util.UUID

import domain.Todo
import scalikejdbc._
import shared.Schema

class TodoRepository extends SQLSyntaxSupport[Todo] {
  override def tableName: String = s"${Schema.Todoist}.todo"
  private val t                  = this.syntax("t")

  def create(todo: Todo)(implicit session: DBSession = autoSession): Todo = {
    val id        = UUID.randomUUID().toString
    val createdAt = Instant.now

    withSQL {
      insert
        .into(this)
        .namedValues(
          column.id          -> id,
          column.description -> todo.description,
          column.createdAt   -> createdAt
        )
    }.update().apply()

    todo.copy(id = id, createdAt = Some(createdAt))
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

    // TODO handle exception or use 'first' instead of 'single'?
  }

  def delete(id: String)(implicit session: DBSession = autoSession): Int = ??? // TODO implement

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
