package com.andymac.astro.orbital;

public class IntegerPoint
{
   public int x,y,z;
   
   public IntegerPoint()
   {
      x = 0;
      y = 0;
      z = 0;
   }
   
   public IntegerPoint(int x, int y)
   {
      this.x = x;
      this.y = y;
      z = 0;
   }
   
   public IntegerPoint(int x, int y, int z)
   {
      this.x = x;
      this.y = y;
      this.z = z;
   }
   @Override
   public boolean equals(Object p)
   {
      if (p == null || !IntegerPoint.class.isAssignableFrom(p.getClass()))
      {
         return false;
      }
      final IntegerPoint point = (IntegerPoint) p;
      if (x == point.x && y == point.y && z == point.z)
         return true;
      return false;
   }
   
   @Override
   public int hashCode()
   {
      int hash = 29;
      hash = 29 * hash + x;
      hash = 29 * hash + y;
      return hash;
   }
}
