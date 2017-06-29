import commons.Commons
import game.Board
import objects._
import org.scalatest._

class MovingTest extends FlatSpec {

    "An object" should "change coordinates" in {
        val movingObject = new MovingObject(x=1, y=2){}
        movingObject.dx = 10
        movingObject.dy = 20
        movingObject.move()
        assert(movingObject.x == 11)
        assert(movingObject.y == 22)
    }
}

class ExplosionTest extends FlatSpec {

    "An dying" should "be set to true" in {
        val movingObject = new MovingObject(x=1, y=2){}
        movingObject.explosion()
        assert(movingObject.dying)
    }
}

class CollisionTest extends FlatSpec {

    "A player" should "collide with a bomb" in {
        val player1 = new Player(1, 1){}
        val bomb1 = new Bomb(1, 1){}
        assert(player1.collisionWith(bomb1))
    }

    "A player" should "not collide with a bomb" in {
        val player2 = new Player(1, 1){}
        val bomb2 = new Bomb(50, 50){}
        assert(!player2.collisionWith(bomb2))
    }
}

class CreateBoardTest extends FlatSpec {

    "A list of guards" should "be created" in {
        val board = new Board(){}
        assert(board.guards.length == 4)
    }
}

class CreateEnemyWaveTest extends FlatSpec {

    "A list of enemies" should "be created" in {
        val enemyWave = new EnemyWave(){}
        assert(enemyWave.enemies.length == 32)
    }
}

class CreateGuardTest extends FlatSpec {

    "A guard" should "be created" in {
        val guard = new Guard(1,1){}
        assert(guard.squares.length == 15)
    }
}

class ReachTheGroundTest extends FlatSpec {

    "A list of enemies" should "not reach a ground" in {
        val enemyWave = new EnemyWave(){}
        enemyWave.enemies.head.visible = true
        assert(!enemyWave.reachedTheGround())
    }

    "A list of enemies" should "reach a ground" in {
        val enemyWave = new EnemyWave(){}
        enemyWave.enemies.head.visible = true
        enemyWave.enemies.head.y = Commons.GUARD_POSY
        assert(enemyWave.reachedTheGround())
    }
}

class SpeedTest extends FlatSpec {

    "A speed" should "be doubled" in {
        val enemyWave = new EnemyWave(){}
        enemyWave.numberOfEnemies = 16
        enemyWave.accelerateIfNeeded()
        assert(enemyWave.enemySpeed == 2)
    }

    "A speed" should "be tripled" in {
        val enemyWave = new EnemyWave(){}
        enemyWave.numberOfEnemies = 8
        enemyWave.accelerateIfNeeded()
        assert(enemyWave.enemySpeed == 3)
    }

    "A speed" should "stay normal" in {
        val enemyWave = new EnemyWave(){}
        enemyWave.numberOfEnemies = 20
        enemyWave.accelerateIfNeeded()
        assert(enemyWave.enemySpeed == 1)
    }
}

class RevivalTest extends FlatSpec {

    "A player" should "revive after being shot by an enemy" in {
        val player = new Player(1, 1){}
        player.dying = true
        player.revive()
        assert(!player.dying)
    }
}