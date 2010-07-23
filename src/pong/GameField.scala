/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pong

import org.lwjgl._
import opengl.{Display,GL11,DisplayMode}
import GL11._
import scala.util.Random
import scala.math._
import tools.RenderPrimitives._

object GameField {

	val size = 10.0f

	val bottom = new Wall(None,new Pos3d)
	val front = new Wall(Some(Main.player2),new Pos3d)
	val back = new Wall(Some(Main.player),new Pos3d)
	val ceiling = new Wall(None,new Pos3d)
	val left = new Wall(None,new Pos3d)
	val right = new Wall(None,new Pos3d)

	bottom.pos.moveAbsolute(0, 0, -size)
	ceiling.pos.moveAbsolute(0, 0, size)
	left.pos.moveAbsolute(-size, 0, 0)
	left.pos.rotate(Pi.toFloat/2, 0, 1, 0)
	right.pos.moveAbsolute(size, 0, 0)
	right.pos.rotate(Pi.toFloat/2, 0, 1, 0)


	def draw = {
		

		TheBall.draw

		bottom.draw
		ceiling.draw
		left.draw
		right.draw
		back.draw
		front.draw

		glColor3f(1,1,1)
		renderBox(size,size,size)

	}
}

object WallPosition extends Enumeration{
	val bottom, front, back, ceiling, left, right = Value
}

class Wall(m_player : Option[Player], m_pos : Pos3d){
	def collision = {
		m_player match {
			case Some(p) =>
			case None =>
		}

	}
	
	def pos = m_pos
	
	def player = m_player

	def draw() {
		glPushMatrix
		glColor3f(1,1,1)
		(player,pos) match {
			case (None,_) => 
				glMultMatrix(pos.getGlMatrix)
				glDisable(GL_LIGHTING)
				renderGrid(GameField.size.toInt)
				glEnable(GL_LIGHTING) 
			case (Some(p),_) =>
				p.draw
		}

		glPopMatrix
	}
}
