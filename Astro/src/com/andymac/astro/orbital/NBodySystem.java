package com.andymac.astro.orbital;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JFrame;

import com.andymac.astro.events.OrbitEvent;
import com.andymac.astro.ui.Chart;

public class NBodySystem
{
   private List<Body> bodies;
   private double tic, duration, maxMeters;
   private double maxX, maxY;
   private int windowSize;
   private Chart chart;
   private Object bodyLock = new Object();
   
   private double currentTime;
   private List<OrbitEvent> orbitEvents;
   
   public NBodySystem(double duration, double tic, double maxMeters, int windowSize)
   {
      this.tic = tic;
      this.duration = duration;
      this.maxMeters = maxMeters;
      this.windowSize = windowSize;
      bodies = new ArrayList<Body>();
      orbitEvents = new ArrayList<OrbitEvent>();
      currentTime = 0;
   }
   
   public void run()
   {
      JFrame frame = new JFrame("Simulation");
      chart = new Chart(this, windowSize);
      frame.setContentPane(chart);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setLocation(200,50);
      frame.pack();
      frame.setVisible(true);
      
//      try
//      {
//         Thread.sleep(10000);
//      } catch (InterruptedException e)
//      {
//         e.printStackTrace();
//      }
      
      double ii = 0;
      while (duration < 0 || ii < duration)
      {
         processTic();
         
         chart.repaint();
         
         try
         {
            Thread.sleep(0, 1);
         } catch (InterruptedException e)
         {
            e.printStackTrace();
         }
         
         ii += tic;
      }
   }
   
   private void processTic()
   {
      maxX = 0;
      maxY = 0;
      for (Body b : bodies)
      {
         b.resetForce();
      }
      
      for (int idx1 = 0; idx1 < bodies.size(); idx1++)
      {
         Body b1 = bodies.get(idx1);
         for (int idx2 = idx1+1; idx2 < bodies.size(); idx2++)
         {
            Body b2 = bodies.get(idx2);
            b1.addForce(b2);
            b2.addForce(b1);
         }
      }
      
      List<OrbitEvent> removable = new ArrayList<OrbitEvent>();
      
      int eventIdx = 0;
      while (eventIdx < orbitEvents.size() && orbitEvents.get(eventIdx).getStartTime() < currentTime)
      {
         OrbitEvent event = orbitEvents.get(eventIdx);
         if (event.getStopTime() < currentTime)
         {
            removable.add(event);
            eventIdx++;
            continue;
         }
         if (currentTime - tic <= event.getStartTime())
         {
            event.processEventStart();
         }
         event.processEvent(tic);
         eventIdx++;
      }
      
      for (OrbitEvent event : removable)
      {
         orbitEvents.remove(event);
         event.processEventEnd();
      }
      
      for (Body b : bodies)
      {
         b.update(tic);
         double x = Math.abs(b.getPos(0));
         double y = Math.abs(b.getPos(1));
         if (x > maxX)
         {
            maxX = x; 
         }
         if (y > maxY)
         {
            maxY = y;
         }
      }
      
      currentTime += tic;
   }
   
   public void paintBodies(Graphics2D g2)
   {
      if (bodies == null)
      {
         return;
      }
      synchronized(bodyLock)
      {
         for (Body b : bodies)
         {
            g2.setColor(b.getColor());
            int x = (int) (b.getPos(0) / maxMeters * windowSize / 2.0 + windowSize/2);
            int y = (int) (b.getPos(1) / maxMeters * windowSize / 2.0 + windowSize/2);
            b.addToQueue(x, y);
            int radius = b.getPixelRadius();
            if (radius < 1)
            {
               radius = (int) (b.getRadius() / maxMeters * windowSize / 2.0);
               if (radius < 1)
               {
                  radius = 1;
               }
               b.setPixelRadius(radius);
            }
            g2.fillOval(x - radius, y - radius, radius*2, radius*2);
            
            g2.setColor(b.getColor().darker());
            for (IntegerPoint p : b.getTrail())
            {
               g2.fillOval(p.x, p.y, 1, 1);
            }
         }
      }
   }
   
   public void addEvent(OrbitEvent event)
   {
      orbitEvents.add(event);
      Collections.sort(orbitEvents);
   }
   
   public void removeEvent(OrbitEvent event)
   {
      orbitEvents.remove(event);
   }
   
   public double getTic()
   {
      return tic;
   }
   
   public void addBody(Body b)
   {
      synchronized(bodyLock)
      {
         bodies.add(b);
      }
   }
   
   public void removeBody(Body b)
   {
      synchronized(bodyLock)
      {
         bodies.remove(b);
      }
   }
   
   public double getMaxX()
   {
      return maxX;
   }
   
   public double getMaxY()
   {
      return maxY;
   }
}
