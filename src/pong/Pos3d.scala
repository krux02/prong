/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pong

import java.nio._
import scala.math._
import org.lwjgl.BufferUtils
import org.lwjgl.util.vector.Matrix4f
import org.lwjgl.util.vector.Vector3f

class Pos3d{
	import Matrix4f._

	val orientation = setIdentity(new Matrix4f)
	//val orientation = new Quaternion
	val position = new Vector3f
	
	def getGlMatrix:FloatBuffer = {
		val buffer = BufferUtils.createFloatBuffer(16)
		val posMat = setIdentity(new Matrix4f).translate(position)
		
		mul(posMat,orientation,posMat)
		posMat.store(buffer)

		buffer.flip
		return buffer;
	}

	def setPosition(x:Float,y:Float,z:Float){
		position.set(x, y, z)
	}

	def moveAbsolute(x:Float,y:Float,z:Float){
		position.translate(x, y, z)
	}

	def moveRelative(x:Float,y:Float,z:Float){
		import orientation._

		val nx = m00*x+m01*y+m02*z
		val ny = m10*x+m11*y+m12*z
		val nz = m20*x+m21*y+m22*z

		position.translate(nx, ny, nz)
	}

	def moveRelative(v:Vector3f){
		moveRelative(v.x,v.y,v.z)
	}

	def getPosition = (position.getX,position.getY,position.getZ)

	def rotate(alpha:Float,x:Float,y:Float,z:Float){
		orientation.rotate(alpha, new Vector3f(x,y,z))
	}

	override def toString:String = {
		return orientation.toString;
	}
}
