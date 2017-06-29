package game
import scala.swing._

object SpaceInvaders extends SimpleSwingApplication {

    def top = new MainFrame {
        title = "SpaceInvaders"
        contents = mainPanel
    }
    def mainPanel = new Board
}
