package objects
import commons.Commons

class Bomb (a: Int = 0, b: Int = 0)
    extends MovingObject(a, b, Commons.BOMB_WIDTH, Commons.BOMB_HEIGHT, dy = Commons.BOMB_SPEED) {

    loadImage("bomb.png")

    override def move() {
        if (y > Commons.GROUND - Commons.BOMB_HEIGHT)
            this.visible = false
        super.move()
    }
}
