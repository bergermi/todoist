package modules

import com.google.inject.AbstractModule
import services.TodoService
import services.impl.TodoServiceImpl

class Module extends AbstractModule {

  override def configure(): Unit = {
    bind(classOf[TodoService]).to(classOf[TodoServiceImpl])
  }
}
