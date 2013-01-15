package com.vylgin.PictureFrame;

import java.awt.Container;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.SwingUtilities;

public class MoveMouseListener implements MouseListener, MouseMotionListener {
    private final Container target;
    private Point startDrag;
    private Point startLoc;

    public MoveMouseListener(final Container target) {
        this.target = target;
    }

    private Point getScreenLocation(final MouseEvent e) {
        final Point cursor = e.getPoint();
        final Point targetLocation = target.getLocationOnScreen();
        return new Point(targetLocation.x + cursor.x, targetLocation.y + cursor.y);
    }

    @Override
    public void mouseClicked(final MouseEvent e) {
    }

    @Override
    public void mouseDragged(final MouseEvent e) {
        final Point current = getScreenLocation(e);
        SwingUtilities.getRoot(target).setLocation(
                new Point(startLoc.x + current.x - startDrag.x, startLoc.y + current.y - startDrag.y));
    }

    @Override
    public void mouseEntered(final MouseEvent e) {
    }

    @Override
    public void mouseExited(final MouseEvent e) {
    }

    @Override
    public void mouseMoved(final MouseEvent e) {
    }

    @Override
    public void mousePressed(final MouseEvent e) {
        startDrag = getScreenLocation(e);
        startLoc = SwingUtilities.getRoot(target).getLocation();
    }

    @Override
    public void mouseReleased(final MouseEvent e) {
    }
}
