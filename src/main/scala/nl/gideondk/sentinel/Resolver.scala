package nl.gideondk.sentinel

import scala.concurrent.Future

import scalaz.stream._

import ReceiverAction._
import TransmitterAction._

trait Resolver[Evt, Cmd]

trait ConsumeResolver[Evt, Cmd] extends Resolver[Evt, Cmd] {
  import ReceiverAction._

  def consume = Consume

  def endStream = EndStream

  def consumeAndEndStream = ConsumeAndEndStream

  def ignore = Ignore
}

trait ResponseResolver[Evt, Cmd] extends Resolver[Evt, Cmd] {
  import TransmitterAction._

  def answer(f: Evt ⇒ Future[Cmd]): Signal[Evt, Cmd] = Signal(f)

  def handle(f: Evt ⇒ Unit): Handle[Evt, Cmd] = Handle(f)

  def consumeStream(f: Evt ⇒ Process[Future, Evt] ⇒ Future[Cmd]): ConsumeStream[Evt, Cmd] = ConsumeStream(f)

  def produce(f: Evt ⇒ Future[Process[Future, Cmd]]): ProduceStream[Evt, Cmd] = ProduceStream(f)

  def react(f: Evt ⇒ Future[Channel[Future, Evt, Cmd]]): ReactToStream[Evt, Cmd] = ReactToStream(f)
}

trait SentinelResolver[Evt, Cmd] extends ConsumeResolver[Evt, Cmd] with ResponseResolver[Evt, Cmd] {
  def process: PartialFunction[Evt, Action]
}