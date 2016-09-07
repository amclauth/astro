package com.andymac.astro.orbital;

public class ClosestApproach
{
   private Body b1, b2;
   private double[] myPos, bPos, myVel, bVel;
   private double dist, deltaV;
   private boolean closing = false;

   public ClosestApproach(Body b1, Body b2)
   {
      this.b1 = b1;
      this.b2 = b2;
      dist = Double.MAX_VALUE-100;
      checkIsCloser();
   }
   
   public boolean checkIsCloser()
   {
      if (!(b1.isActive && b2.isActive))
         return false;
      double newDist = b1.getDistance(b2);
//      System.out.println(newDist + " ::: " + dist);
      if (newDist < dist)
      {
         closing = true;
         myPos = b1.getPos().clone();
         myVel = b1.getVel().clone();
         bPos = b2.getPos().clone();
         bVel = b2.getVel().clone();
         dist = ClosestApproach.scalarDifference(myPos, bPos);
         deltaV = ClosestApproach.scalarDifference(myVel, bVel);
         return true;
      }
      if (closing)
      {
         print();
         closing = false;
      }
      return false;
   }
   
   public void print()
   {
      System.out.println(b1.getName() + " => " + b2.getName());
      System.out.println("   P" + Body.fString(myPos) + " V" + Body.fString(myVel));
      System.out.println("   P" + Body.fString(bPos) + " V" + Body.fString(bVel));
      System.out.println(String.format("   D: %5.2e V: %5.2e", dist, deltaV));
   }
   
   public static double scalarDifference(double[] v1, double[] v2)
   {
      if (v1.length != v2.length)
      {
         System.out.println("ERROR: Unequal length vectors.");
         return 0;
      }
      double sumOfSquares = 0;
      for (int ii = 0; ii < v1.length; ii++)
      {
         sumOfSquares += (v1[ii] - v2[ii]) * (v1[ii] - v2[ii]);
      }
//      System.out.println(sumOfSquares);
      return Math.sqrt(sumOfSquares);
   }
}
