package com.andymac.astro.events;

import com.andymac.astro.orbital.Body;

public class DeltaVEvent extends OrbitEvent
{
   
   public DeltaVEvent(Body body, double startTime, double stopTime)
   {
      setBody(body);
      setStartTime(startTime);
      setStopTime(stopTime);
   }
   
   @Override
   public void processEventStart()
   {
      
   }

   @Override
   public void processEvent(double tic)
   {
//      if (body.getVel(1) < 0 && Math.abs(body.getVel(0)) < Math.abs(body.getVel(1)))
      body.addForce(-250,-250,0);
   }

   @Override
   public void processEventEnd()
   {
        
   }

}
