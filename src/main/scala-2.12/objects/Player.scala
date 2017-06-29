package objects
import commons.Commons

class Player(a: Int, b: Int, var missile: Missile = new Missile())
    extends MovingObject(a, b, Commons.PLAYER_WIDTH, Commons.PLAYER_HEIGHT) {

    loadImage("player.png")
    missile.visible = false

    def revive() {
        loadImage("player.png")
        dying = false
        x = Commons.B_WIDTH / 2
    }

    def missileMove() {
        if (missile.visible)
            missile move()
    }

    override def move() {
        if (x > Commons.B_WIDTH - Commons.PLAYER_WIDTH)
            x = Commons.B_WIDTH - Commons.PLAYER_WIDTH
        else if (x < 0)
            x = 0
        else
            super.move()
    }
}
