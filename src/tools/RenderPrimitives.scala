/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tools

import org.lwjgl.opengl.GL11._
import org.lwjgl.util.glu._
import org.newdawn.slick.opengl.Texture
import org.lwjgl.BufferUtils.createFloatBuffer

object RenderPrimitives {
	val quadric:Quadric = new Quadric

	def renderGrid(size : Int){
		glBegin(GL_LINES)
		for(i <- -size to size){
			glVertex2i(i,-size)
			glVertex2i(i, size)
			glVertex2i(-size,i)
			glVertex2i( size,i)
		}
		glEnd()
	}

	def renderBox(sx:Float,sy:Float,sz:Float) {

		glPushMatrix
		glScalef(sx,sy,sz)
		glBegin(GL_LINES)

		for(x <- 1 to 3; a <- Range(-1,2,2);b <- Range(-1,2,2); c <- Range(-1,2,2))
			x match{
				case 1 => glVertex3f(a, b, c)
				case 2 => glVertex3f(c, a, b)
				case 3 => glVertex3f(b, c, a)
			}
		glEnd()
		glPopMatrix
	}

	def centeredRect(sx:Float, sy:Float){
		glBegin(GL_LINE_LOOP)
			glVertex2f(-sx,-sy)
			glVertex2f(sx,-sy)
			glVertex2f(sx,sy)
			glVertex2f(-sx,sy)
		glEnd

		glBegin(GL_QUADS)
			glTexCoord2f(0,0)
			glVertex2f(-sx,-sy)
			glTexCoord2f(1,0)
			glVertex2f(sx,-sy)
			glTexCoord2f(1,1)
			glVertex2f(sx,sy)
			glTexCoord2f(0,1)
			glVertex2f(-sx,sy)
		glEnd
	}

	def rect(sx:Float, sy:Float){
		glBegin(GL_LINE_LOOP)
		glVertex2f(0,0)
		glVertex2f(0,sy)
		glVertex2f(sx,sy)
		glVertex2f(sx,0)
		glEnd

		glBegin(GL_QUADS)
		glVertex2f(0,0)
		glVertex2f(sx,0)
		glVertex2f(sx,sy)
		glVertex2f(0,sy)

		glEnd
	}

	def sphere(r:Float){
		import GLU._
		val sphere:Sphere = new Sphere//quadric.asInstanceOf[Sphere]
		sphere.setNormals(GLU_SMOOTH)
		sphere.setDrawStyle(GLU_FILL)
		//sphere.setDrawStyle(GLU_LINE)
		sphere.draw(r, 18, 9)
	}

	def renderThing {
		glBegin(GL_TRIANGLE_FAN)
		glNormal3d(0,0,1)
		glVertex3d(0,0,0.5)
		glNormal3d(-1,-1,1)
		glVertex2d(-0.5, -0.5)
		glNormal3d(1,-1,1)
		glVertex2d(0.5, -0.5)
		glNormal3d(1, 1, 1)
		glVertex2d(0.5, 0.5)
		glNormal3d(-1, 1, 1)
		glVertex2d(-0.5, 0.5)
		glNormal3d(-1, -1, 1)
		glVertex2d(-0.5, -0.5)
		glEnd
	}
}



/*
 * val vertices = createFloatBuffer(2*4)
 * vertices.put(Array(w,h, 0f,h, 0f,0f, w,0f))
 * vertices.rewind
 * glEnableClientState(GL_VERTEX_ARRAY)
 * glVertexPointer(2, 0, vertices)
 * glDrawArrays(GL_QUADS,0,4)
 */
