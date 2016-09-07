package com.andymac.astro.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import com.andymac.astro.orbital.NBodySystem;

public class Chart extends JPanel
{
   private static final long serialVersionUID = 1L;
   private NBodySystem system;
   private int windowSize;
   Color color;

   public Chart(NBodySystem system, int windowSize)
   {
      this.system = system;
      this.windowSize = windowSize;
      initComponents();
   }

   @Override
   public void paint(Graphics g)
   {
      super.paint(g);
      Graphics2D g2 = (Graphics2D) g;

      if (system != null)
      {
         system.paintBodies(g2);
      }
   }

   private void initComponents()
   {
      setSize(windowSize, windowSize);
      setPreferredSize(new Dimension(windowSize, windowSize));

      setBackground(new Color(0, 0, 0));
   }
}
