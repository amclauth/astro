package com.andymac.astro.events;

import com.andymac.astro.orbital.Body;

public abstract class OrbitEvent implements Comparable<OrbitEvent>
{
   protected Body body;
   protected double startTime, stopTime;

   public abstract void processEventStart();
   
   public abstract void processEvent(double tic);

   public abstract void processEventEnd();

   public int compareTo(OrbitEvent orbitEvent)
   {
      return Double.compare(startTime, orbitEvent.startTime);
   }

   public Body getBody()
   {
      return body;
   }

   public void setBody(Body body)
   {
      this.body = body;
   }

   public double getStartTime()
   {
      return startTime;
   }

   public void setStartTime(double startTime)
   {
      this.startTime = startTime;
   }

   public double getStopTime()
   {
      return stopTime;
   }

   public void setStopTime(double stopTime)
   {
      if (stopTime < 0)
      {
         this.stopTime = Double.MAX_VALUE;
      }
      else
      {
         this.stopTime = stopTime;
      }
   }
}
