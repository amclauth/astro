package com.andymac.astro.events;

import com.andymac.astro.orbital.Body;
import com.andymac.astro.orbital.NBodySystem;

public class NewBodyEvent extends OrbitEvent
{
   private NBodySystem system;
   private Body startAtBody;
   private double deltaV = 0;
   private double angle = 0;
   
   public NewBodyEvent(Body body, double startTime, double stopTime, NBodySystem system)
   {
      body.isActive = false;
      setBody(body);
      setStartTime(startTime);
      setStopTime(stopTime);
      this.system = system;
   }
   
   public void startAtBody(Body body, double deltaV, double angle)
   {
      this.startAtBody = body;
      this.deltaV = deltaV;
      this.angle = angle;
   }
   
   @Override
   public void processEventStart()
   {
      body.isActive = true;
      if (startAtBody != null)
      {
         body.setPos(startAtBody.getPos());
         
         // TODO start with just adding it in the same direction ... need to add it at various
         // angles later
         double vx = startAtBody.getVel(0);
         double vy = startAtBody.getVel(1);
         double vz = startAtBody.getVel(2);
         
         double vel = Math.sqrt(vx*vx + vy*vy + vz*vz);
         double ratio = vel / (vel + deltaV);

//         System.out.println(vel + " : " + deltaV);
         body.setVel(vx / ratio, vy / ratio, vz / ratio);
//         System.out.println(body + " : " + ratio);
      }
      system.addBody(body);
//      System.out.println("Starting");
   }
   
   @Override
   public void processEvent(double tic)
   {
      
   }

   @Override
   public void processEventEnd()
   {
      body.isActive = false;
      system.removeBody(body);
   }

}
