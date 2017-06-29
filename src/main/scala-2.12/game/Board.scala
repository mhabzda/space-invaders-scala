package game
import java.awt.Color
import javax.swing.{AbstractAction, Timer => SwingTimer}
import commons.Commons
import objects.{EnemyWave, Guard, Player}
import scala.collection.mutable.ListBuffer
import scala.swing._
import scala.swing.event.{Key, KeyPressed, KeyReleased}

class Board(var inGame: Boolean = true, var lives: Int = 3,
            var player: Player = new Player(Commons.START_X, Commons.START_Y),
            var enemyWave: EnemyWave = new EnemyWave, var message: String = "",
            var guards: ListBuffer[Guard] = new ListBuffer[Guard]()) extends Panel {

    for (i <- 0 to 3)
        guards += new Guard(Commons.GUARD_POSX + i * 125, Commons.GUARD_POSY)

    preferredSize = new Dimension(Commons.B_WIDTH, Commons.B_HEIGHT)
    focusable = true
    listenTo(keys)

    reactions += {
        case KeyPressed(_, key, _, _) =>
            onKeyPressed(key)
            repaint
        case KeyReleased(_, key, _, _) =>
            onKeyReleased(key)
            repaint
    }

    val timer = new SwingTimer(10, new AbstractAction() {
        def actionPerformed(e: java.awt.event.ActionEvent) {
            if(inGame) {
                repaint
                animationCycle()
            }
        }
    })
    timer.start()


    private def gameOver(g: Graphics2D) {
        font = new Font("Helvetica", 0, 18)
        g setColor Color.WHITE
        g setFont font
        var shift = 50
        if(message(0) == 'Y')
            shift = 85
        g drawString(message, Commons.B_WIDTH / 2 - shift, Commons.B_HEIGHT / 2)
    }

     override def paint(g: Graphics2D) {

        g setColor Color.BLACK
        g fillRect (0, 0, size.width, size.height)

        if(!inGame) {
            gameOver(g)
            return
        }

        font = new Font("Helvetica", 0 , 15)
        g setColor Color.WHITE
        g setFont font

        g drawString ("Lives: " + lives.toString, Commons.B_WIDTH - 90, 20)
        g drawString ("Enemies Left: " + enemyWave.numberOfEnemies.toString, 28, 20)

        g setColor Color.GREEN
        g drawLine (0, Commons.GROUND, Commons.B_WIDTH, Commons.GROUND)

        player draw (g, this)
        if (player.missile.visible)
            player.missile draw(g, this)

        enemyWave draw (g, this)

        for (guard <- guards) {
            guard draw g
        }
    }

    private def animationCycle() {
        if (enemyWave.numberOfEnemies == 0) {
            inGame = false
            message = "You Won! Congrats!"
        }
        if (player.dying) {
            lives -= 1
            if (lives != 0) player revive()
            else {
                inGame = false
                message = "Game Over!"
            }
        }
        if (enemyWave reachedTheGround()) {
            inGame = false
            message = "Game Over!"
        }
        player move()
        player missileMove()
        enemyWaveMove()
        collisionMissileEnemies()
        collisionBombPlayer()
        collisionWithGuards()
    }

    private def enemyWaveMove() {
        enemyWave fixStatus()
        enemyWave bombMove()
        enemyWave shooting()
        enemyWave accelerateIfNeeded()
        enemyWave turnAroundIfHitTheWall()
    }

    private def collisionMissileEnemies() {
        if (player.missile.visible) {
            for (enemy <- enemyWave.enemies)
                if (enemy.visible && (player.missile collisionWith enemy)) {
                    enemy explosion()
                    enemyWave.numberOfEnemies -= 1
                    player.missile.visible = false
            }
        }
    }

    private def collisionBombPlayer() {
        for (enemy <- enemyWave.enemies) {
            if (enemy.bomb.visible && (enemy.bomb collisionWith player)) {
                player explosion()
                enemy.bomb.visible = false
            }
        }
    }

    private def collisionWithGuards() {
        for (guard <- guards) {
            guard collisionWith player.missile
            for (enemy <- enemyWave.enemies) {
                guard collisionWith enemy.bomb
            }
        }
    }

    def onKeyPressed(key: _root_.scala.swing.event.Key.Value): Any = key match {
        case Key.Left => player.dx = -Commons.PLAYER_SPEED
        case Key.Right => player.dx = Commons.PLAYER_SPEED
        case Key.Space => if (!player.missile.visible) {
            player.missile.x = player.x + Commons.PLAYER_WIDTH / 2
            player.missile.y = player.y
            player.missile.visible = true
        }
        case _ =>
    }

    def onKeyReleased(key: _root_.scala.swing.event.Key.Value): Any = key match {
        case Key.Left => player.dx = 0
        case Key.Right => player.dx = 0
        case _ =>
    }
}

