package objects
import java.awt.{Color, Graphics, Rectangle}

import commons.Commons

class Square (x: Int, y: Int, var visible: Boolean = true) extends Rectangle(x, y, Commons.SQUARE_SIZE, Commons.SQUARE_SIZE) {

    def draw(g: Graphics) {
        g setColor new Color(241, 59, 53)
        g fillRect(x, y, width, height)
    }
}
