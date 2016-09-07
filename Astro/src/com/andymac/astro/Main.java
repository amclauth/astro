package com.andymac.astro;

import java.awt.Color;

import com.andymac.astro.events.DeltaVEvent;
import com.andymac.astro.events.NewBodyEvent;
import com.andymac.astro.orbital.Body;
import com.andymac.astro.orbital.NBodySystem;

@SuppressWarnings("unused")
public class Main
{
   // TODO if the number of bodies is small (3 or less?), add a printout that
   // shows the closest approach along
   // with their velocity vectors between any two bodies. Also show closest
   // approach to the other's orbit
   // if the "trails" are still active.
   public static void main(String[] args)
   {
      NBodySystem system = solarSailExample();
//      NBodySystem system = dropshipIsolation1();

      system.run();
   }
   
   private static NBodySystem solarSailExample()
   {
      NBodySystem system = new NBodySystem(-1, 1, 10e6, 800);
      addEarth(system);
      Body station = getStation();
      system.addBody(station);
      
      Body ship = getDropship();
      DeltaVEvent ds1 = new DeltaVEvent(ship, 0, -1);
      system.addEvent(ds1);
      system.addBody(ship);
      ship.setMaxTrailSize(10000);
      
      Body sat2 = getDropship();

      NewBodyEvent ds2 = new NewBodyEvent(sat2, 1 * 15 * 60, -1, system);
      ds2.startAtBody(station, -610, 0);
      system.addEvent(ds2);
      
      return system;
   }

   private static NBodySystem earthMoonSystem()
   {
      NBodySystem system = new NBodySystem(-1, 1, 400e6, 800);
      addEarth(system);
      addMoon(system);
      return system;
   }

   private static NBodySystem dropshipView()
   {
      NBodySystem system = new NBodySystem(-1, 1, 10e6, 800);
      addEarth(system);
      Body station = getStation();
      system.addBody(station);

      for (double r = 6.5e6; r < 9e6; r += 0.5e6)
      {
         addDebris(r, system);
      }

      for (int ii = 0; ii < 8; ii++)
      {
         NewBodyEvent ds1 = new NewBodyEvent(getDropship(), ii * 15 * 60, -1, system);
         ds1.startAtBody(station, -610, 0);
         system.addEvent(ds1);
      }

      return system;
   }

   private static NBodySystem dropshipIsolation1()
   {
      NBodySystem system = new NBodySystem(-1, 1, 10e6, 800);
      addEarth(system);
      Body station = getStation();
      system.addBody(station);
      
      Body sat1 = getDropship();
      Body sat2 = getDropship();

      NewBodyEvent ds1 = new NewBodyEvent(sat1, 0, -1, system);
      ds1.startAtBody(station, -610, 0);
      system.addEvent(ds1);
      NewBodyEvent ds2 = new NewBodyEvent(sat2, 4 * 15 * 60, -1, system);
      ds2.startAtBody(station, -610, 0);
      system.addEvent(ds2);
      sat1.addClosestApproachBody(sat2);

      return system;
   }
   
   private static NBodySystem dropshipOrbitIntersection()
   {
      NBodySystem system = new NBodySystem(-1, 1, 10e6, 800);

      addEarth(system);
      Body station = getStation();
      system.addBody(station);

      Body sat1 = getDropship();
      Body sat2 = getDropship();

      sat1.setPos(-9371393, 0, 0);
      sat1.setVel(0, 5925.77, 0);

      sat2.setPos(-7.59e6, 5.5e6, 0);
      sat2.setVel(3.47e3, 4.79e3, 0);

      system.addBody(sat1);
      NewBodyEvent ds1 = new NewBodyEvent(sat2, 60 * 8.42, -1, system);
      system.addEvent(ds1);

      sat1.addClosestApproachBody(sat2);
      
      return system;
   }

   ////////////////////////////////////////////////////////////////////
   ////////////////////////////////////////////////////////////////////
   ////////////////////////////////////////////////////////////////////
   
   private static Body getStation()
   {
      Body station = new Body("Station");
      station.setMass(500000);
      station.setPos(-9371393, 0, 0);
      station.setVel(0, 6522, 0);
      station.setColor(Color.green);
      station.setPixelRadius(3);
      station.setMaxTrailSize(1500);
      return station;
   }

   private static Body getDropship()
   {
      Body ship = new Body("Dropship");
      ship.setMass(10000);
      ship.setPos(-9371393, 0, 0);
      ship.setVel(0, 5925.77, 0);
      ship.setColor(Color.red);
      ship.setPixelRadius(2);
      ship.setMaxTrailSize(1500);
      return ship;
   }

   private static void addEarth(NBodySystem system)
   {
      Body earth = new Body("Earth");
      earth.setMass(5.972e24);
      earth.setPos(0, 0, 0);
      earth.setVel(0, 0, 0);
      earth.setColor(Color.blue);
      earth.setRadius(6.371393e6);
      system.addBody(earth);
   }

   private static void addDebris(double radiusBand, NBodySystem system)
   {
      int max = 50;
      double vel = Math.sqrt(Body.G * (5.972e24 / radiusBand));
      for (int ii = 0; ii < max; ii++)
      {
         Body debris = new Body("Debris");

         debris.setMass(1);
         debris.setPos(-Math.cos(Math.PI * 2.0 / max * ii) * radiusBand,
               -Math.sin(Math.PI * 2.0 / max * ii) * radiusBand, 0);
         debris.setVel(-Math.sin(Math.PI * 2.0 / max * ii) * vel, Math.cos(Math.PI * 2.0 / max * ii) * vel, 0);
         debris.setColor(Color.white);
         debris.setPixelRadius(1);
         system.addBody(debris);
      }
   }

   private static void addMoon(NBodySystem system)
   {
      Body moon = new Body("Moon");
      moon.setMass(7.347e22);
      moon.setPos(384e6, 0, 0);
      moon.setVel(0, 1022, 0);
      moon.setColor(Color.white);
      moon.setRadius(1.737e6);
      system.addBody(moon);
   }

}
