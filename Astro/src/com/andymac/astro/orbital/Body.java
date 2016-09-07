package com.andymac.astro.orbital;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Body
{
	private double mass = 1;
	private double radius = 0;
	private double pos[] = new double[3];
	private double vel[] = new double[3];
	private double force[] = new double[3];
	private int pixelRadius = 0;
	public static final double G = 6.673e-11;
	private String name = "";
	private Color color;
	private int maxTrailSize;
	private Queue<IntegerPoint> trail = new ConcurrentLinkedQueue<IntegerPoint>();
	private Map<Body,ClosestApproach> closestMap = new HashMap<Body,ClosestApproach>();
	public boolean isActive = true;
	
	public Body() {};
	
	public Body(String name)
	{
		this.name = name;
	}
	
	double getDistance(Body b)
	{
	   if (!isActive || !b.isActive)
	   {
	      return Double.MAX_VALUE;
	   }
		double dPos[] = getVectorDistance(b);
		return Math.sqrt(dPos[0]*dPos[0] + dPos[1]*dPos[1] + dPos[2]*dPos[2]);
	}
	
	double[] getVectorDistance(Body b)
	{
	   if (!isActive || !b.isActive)
      {
         return null;
      }
	   double dPos[] = new double[3];
      dPos[0] = b.getPos(0) - pos[0];
      dPos[1] = b.getPos(1) - pos[1];
      dPos[2] = b.getPos(2) - pos[2];
      return dPos;
	}
	
	double[] getVectorDeltaV(Body b)
	{
	   if (!isActive || !b.isActive)
      {
         return null;
      }
	   double dVel[] = new double[3];
	   dVel[0] = b.getVel(0) - vel[0];
	   dVel[1] = b.getVel(1) - vel[1];
	   dVel[2] = b.getVel(2) - vel[2];
	   return dVel;
	}
	
	double getDeltaV(Body b)
	{
	   if (!isActive || !b.isActive)
      {
         return Double.MAX_VALUE;
      }
	   double dVel[] = getVectorDeltaV(b);
      return Math.sqrt(dVel[0]*dVel[0] + dVel[1]*dVel[1] + dVel[2]*dVel[2]);
	}
	
	public void update(double dt)
	{
//	   System.out.println(this);
      vel[0] += dt * force[0] / mass;
		vel[1] += dt * force[1] / mass;
		vel[2] += dt * force[2] / mass;
		pos[0] += dt * vel[0];
		pos[1] += dt * vel[1];
		pos[2] += dt * vel[2];
		for (ClosestApproach closest : closestMap.values())
		{
		   closest.checkIsCloser();
		}
	}
	
	public void addToQueue(int x, int y)
	{
	   if (maxTrailSize <= 0)
	   {
	      return;
	   }
	   
	   IntegerPoint p = new IntegerPoint(x,y);
	   if (!trail.contains(p))
	   {
	      trail.add(p);
	   }
	   if (trail.size() > maxTrailSize)
	   {
	      trail.remove();
	   }
	}
	
	public Queue<IntegerPoint> getTrail()
	{
	   return trail;
	}
	
	void addForce(Body b)
	{
		double dist = getDistance(b);
		if (dist == 0)
		   return;
		double F = (G * mass * b.getMass()) / (dist*dist);
		double dPos[] = new double[3];
      dPos[0] = b.getPos(0) - pos[0];
      dPos[1] = b.getPos(1) - pos[1];
      dPos[2] = b.getPos(2) - pos[2];
		force[0] += F * dPos[0] / dist;
		force[1] += F * dPos[1] / dist;
		force[2] += F * dPos[2] / dist;
	}
	
	void resetForce()
	{
		force[0]=0;
		force[1]=0;
		force[2]=0;
	}
	
	public void addForce(double fx, double fy, double fz)
	{
	   force[0] += fx;
	   force[1] += fy;
	   force[2] += fz;
	}
	
	public static String fString(double[] dArr)
	{
	   return String.format("(%5.2e,%5.2e,%5.2e)", dArr[0], dArr[1], dArr[2]);
	}
	
	public String toString()
	{
	   return name + ": " + 
	         "P(" + fString(pos) + ") " +
	         "V(" + fString(vel) + ") " + 
	         "F(" + fString(force) + ")";
	}
	
	/////////////////////////
	// GETTERS AND SETTERS //
	/////////////////////////
	
	public String getName()
	{
	   return name;
	}
	
	public double getMass()
	{
		return mass;
	}

	public void setMass(double mass)
	{
		this.mass = mass;
	}

	public double getRadius()
	{
		return radius;
	}

	public void setRadius(double radius)
	{
		this.radius = radius;
	}
	
	public double[] getPos()
	{
		return pos;
	}
	
	public double getPos(int idx)
	{
		return pos[idx];
	}
	
	public void setPos(double[] pos)
	{
		this.pos[0] = pos[0];
		this.pos[1] = pos[1];
		this.pos[2] = pos[2];
	}
	
	public void setPos(double x, double y, double z)
	{
		pos[0] = x;
		pos[1] = y;
		pos[2] = z;
	}
	
	public double[] getVel()
	{
		return vel;
	}
	
	public double getVel(int idx)
	{
		return vel[idx];
	}
	
	public void setVel(double[] vel)
	{
		this.vel[0] = vel[0];
		this.vel[1] = vel[1];
		this.vel[2] = vel[2];
	}
	
	public void setVel(double vx, double vy, double vz)
	{
		vel[0] = vx;
		vel[1] = vy;
		vel[2] = vz;
	}
	
	public void setColor(Color c)
	{
		color = c;
	}
	
	public Color getColor()
	{
		return color;
	}

   public int getPixelRadius()
   {
      return pixelRadius;
   }

   public void setPixelRadius(int pixelRadius)
   {
      this.pixelRadius = pixelRadius;
   }
   
   public void setMaxTrailSize(int maxTrailSize)
   {
      this.maxTrailSize = maxTrailSize;
   }
   
   public void addClosestApproachBody(Body b)
   {
      closestMap.put(b, new ClosestApproach(this,b));
   }

}
