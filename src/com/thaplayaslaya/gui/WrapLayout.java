package com.thaplayaslaya.gui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;

import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

/**
 * FlowLayout subclass that fully supports wrapping of components. Taken from
 * <https://tips4java.wordpress.com/2008/11/06/wrap-layout/> Modified to include
 * option to maximize horizontal dimension
 */
public class WrapLayout extends FlowLayout {

	private static final long serialVersionUID = -535249657304983386L;
	// private Dimension preferredLayoutSize;
	private boolean maximizeHorizontalDimension = false;

	public void setMaximizeHorizontalDimension(boolean max) {
		maximizeHorizontalDimension = max;
	}

	public boolean isMaximizeHorizontalDimension() {
		return maximizeHorizontalDimension;
	}

	/**
	 * Constructs a new <code>WrapLayout</code> with a left alignment and a
	 * default 5-unit horizontal and vertical gap.
	 */
	public WrapLayout() {
		super();
	}

	/**
	 * Constructs a new <code>FlowLayout</code> with the specified alignment and
	 * a default 5-unit horizontal and vertical gap. The value of the alignment
	 * argument must be one of <code>WrapLayout</code>, <code>WrapLayout</code>,
	 * or <code>WrapLayout</code>.
	 * 
	 * @param align
	 *            the alignment value
	 */
	public WrapLayout(int align) {
		super(align);
	}

	/**
	 * Creates a new flow layout manager with the indicated alignment and the
	 * indicated horizontal and vertical gaps.
	 * <p>
	 * The value of the alignment argument must be one of
	 * <code>WrapLayout</code>, <code>WrapLayout</code>, or
	 * <code>WrapLayout</code>.
	 * 
	 * @param align
	 *            the alignment value
	 * @param hgap
	 *            the horizontal gap between components
	 * @param vgap
	 *            the vertical gap between components
	 */
	public WrapLayout(int align, int hgap, int vgap) {
		super(align, hgap, vgap);
	}

	/**
	 * Centers the elements in the specified row, if there is any slack.
	 * 
	 * @param target
	 *            the component which needs to be moved
	 * @param x
	 *            the x coordinate
	 * @param y
	 *            the y coordinate
	 * @param width
	 *            the width dimensions
	 * @param height
	 *            the height dimensions
	 * @param rowStart
	 *            the beginning of the row
	 * @param rowEnd
	 *            the the ending of the row
	 * @param useBaseline
	 *            Whether or not to align on baseline.
	 * @param ascent
	 *            Ascent for the components. This is only valid if useBaseline
	 *            is true.
	 * @param descent
	 *            Ascent for the components. This is only valid if useBaseline
	 *            is true.
	 * @return actual row height
	 */
	private int moveComponents(Container target, int x, int y, int width, int height, int rowStart, int rowEnd, boolean ltr, boolean useBaseline,
			int[] ascent, int[] descent) {
		switch (getAlignment()) {
		case LEFT:
			x += ltr ? 0 : width;
			break;
		case CENTER:
			x += width / 2;
			break;
		case RIGHT:
			x += ltr ? width : 0;
			break;
		case LEADING:
			break;
		case TRAILING:
			x += width;
			break;
		}
		int maxAscent = 0;
		int nonbaselineHeight = 0;
		int baselineOffset = 0;
		if (useBaseline) {
			int maxDescent = 0;
			for (int i = rowStart; i < rowEnd; i++) {
				Component m = target.getComponent(i);
				if (m.isVisible()) {
					if (ascent[i] >= 0) {
						maxAscent = Math.max(maxAscent, ascent[i]);
						maxDescent = Math.max(maxDescent, descent[i]);
					} else {
						nonbaselineHeight = Math.max(m.getHeight(), nonbaselineHeight);
					}
				}
			}
			height = Math.max(maxAscent + maxDescent, nonbaselineHeight);
			baselineOffset = (height - maxAscent - maxDescent) / 2;
		}
		for (int i = rowStart; i < rowEnd; i++) {
			Component m = target.getComponent(i);
			if (m.isVisible()) {
				int cy;
				if (useBaseline && ascent[i] >= 0) {
					cy = y + baselineOffset + maxAscent - ascent[i];
				} else {
					cy = y + (height - m.getHeight()) / 2;
				}
				if (ltr) {
					m.setLocation(x, cy);
				} else {
					m.setLocation(target.getWidth() - x - m.getWidth(), cy);
				}
				x += m.getWidth() + getHgap();
			}
		}
		return height;
	}

	/**
	 * Lays out the container. This method lets each <i>visible</i> component
	 * take its preferred size by reshaping the components in the target
	 * container in order to satisfy the alignment of this
	 * <code>FlowLayout</code> object.
	 * 
	 * @param target
	 *            the specified component being laid out
	 * @see Container
	 * @see java.awt.Container#doLayout
	 */
	@Override
	public void layoutContainer(Container target) {
		synchronized (target.getTreeLock()) {
			Insets insets = target.getInsets();
			int maxwidth = target.getWidth() - (insets.left + insets.right + getHgap() * 2);
			int nmembers = target.getComponentCount();
			int x = 0, y = insets.top + getVgap();
			int rowh = 0, start = 0;

			boolean ltr = target.getComponentOrientation().isLeftToRight();

			boolean useBaseline = getAlignOnBaseline();
			int[] ascent = null;
			int[] descent = null;

			if (useBaseline) {
				ascent = new int[nmembers];
				descent = new int[nmembers];
			}

			for (int i = 0; i < nmembers; i++) {
				Component m = target.getComponent(i);
				if (m.isVisible()) {
					Dimension d = m.getPreferredSize();
					if (maximizeHorizontalDimension)
						d.width = Math.min(m.getMaximumSize().width, maxwidth);

					m.setSize(d.width, d.height);

					if (useBaseline) {
						int baseline = m.getBaseline(d.width, d.height);
						if (baseline >= 0) {
							ascent[i] = baseline;
							descent[i] = d.height - baseline;
						} else {
							ascent[i] = -1;
						}
					}
					// Can component fit on this row?
					if ((x == 0) || ((x + d.width) <= maxwidth)) {
						if (x > 0) {
							x += getHgap();
						}
						x += d.width;
						rowh = Math.max(rowh, d.height);
					}
					// If not, Make a new row:
					else {
						// First get the height of the row were are working on
						rowh = moveComponents(target, insets.left + getHgap(), y, maxwidth - x, rowh, start, i, ltr, useBaseline, ascent, descent);
						// The component we were adding is now on the next row,
						// Set the x to the component's width
						x = d.width;
						// Add the row height of the previous row, and the
						// vertical gap amount
						// to the old y value to get the new y value
						y += getVgap() + rowh;
						// now reset the row height to the height of the only
						// component in that row
						rowh = d.height;
						// Set the starting point of this row to the index of
						// the component that
						// forced us to make a new row for it. We'll use this in
						// calls to moveComponents
						start = i;
					}
				}
			}
			moveComponents(target, insets.left + getHgap(), y, maxwidth - x, rowh, start, nmembers, ltr, useBaseline, ascent, descent);
		}
	}

	/**
	 * Returns the preferred dimensions for this layout given the <i>visible</i>
	 * components in the specified target container.
	 * 
	 * @param target
	 *            the component which needs to be laid out
	 * @return the preferred dimensions to lay out the subcomponents of the
	 *         specified container
	 */
	@Override
	public Dimension preferredLayoutSize(Container target) {
		return layoutSize(target, true);
	}

	/**
	 * Returns the minimum dimensions needed to layout the <i>visible</i>
	 * components contained in the specified target container.
	 * 
	 * @param target
	 *            the component which needs to be laid out
	 * @return the minimum dimensions to lay out the subcomponents of the
	 *         specified container
	 */
	@Override
	public Dimension minimumLayoutSize(Container target) {
		Dimension minimum = layoutSize(target, false);
		minimum.width -= (getHgap() + 1);
		return minimum;
	}

	/**
	 * Returns the minimum or preferred dimension needed to layout the target
	 * container.
	 * 
	 * @param target
	 *            target to get layout size for
	 * @param preferred
	 *            should preferred size be calculated
	 * @return the dimension to layout the target container
	 */
	private Dimension layoutSize(Container target, boolean preferred) {
		synchronized (target.getTreeLock()) {
			// Each row must fit with the width allocated to the container.
			// When the container width = 0, the preferred width of the
			// container
			// has not yet been calculated so lets ask for the maximum.

			int targetWidth = target.getSize().width;
			// something happens when targetWidth == 360;

			System.out.println(targetWidth);
			if (targetWidth == 0)
				targetWidth = Integer.MAX_VALUE;

			int hgap = getHgap();
			int vgap = getVgap();
			Insets insets = target.getInsets();
			int horizontalInsetsAndGap = insets.left + insets.right + (hgap * 2);
			int maxWidth = targetWidth - horizontalInsetsAndGap;

			// Fit components into the allowed width

			Dimension dim = new Dimension(0, 0);
			int rowWidth = 0;
			int rowHeight = 0;

			int nmembers = target.getComponentCount();

			for (int i = 0; i < nmembers; i++) {
				Component m = target.getComponent(i);

				if (m.isVisible()) {
					Dimension d = preferred ? m.getPreferredSize() : m.getMinimumSize();
					if (maximizeHorizontalDimension)
						d.width = Math.min(m.getMaximumSize().width, maxWidth);
					// Can't add the component to current row. Start a new row.

					if (rowWidth + d.width > maxWidth) {
						addRow(dim, rowWidth, rowHeight);
						rowWidth = 0;
						rowHeight = 0;
					}

					// Add a horizontal gap for all components after the first

					if (rowWidth != 0) {
						rowWidth += hgap;
					}

					rowWidth += d.width;
					rowHeight = Math.max(rowHeight, d.height);
				}
			}

			addRow(dim, rowWidth, rowHeight);

			dim.width += horizontalInsetsAndGap;
			dim.height += insets.top + insets.bottom + vgap * 2;

			// When using a scroll pane or the DecoratedLookAndFeel we need to
			// make sure the preferred size is less than the size of the
			// target containter so shrinking the container size works
			// correctly. Removing the horizontal gap is an easy way to do this.

			Container scrollPane = SwingUtilities.getAncestorOfClass(JScrollPane.class, target);

			if (scrollPane != null && target.isValid()) {
				dim.width -= (hgap + 1);
			}
			System.out.println(dim);
			return dim;
		}
	}

	int rowcount = 0;

	/*
	 * A new row has been completed. Use the dimensions of this row to update
	 * the preferred size for the container.
	 * 
	 * @param dim update the width and height when appropriate
	 * 
	 * @param rowWidth the width of the row to add
	 * 
	 * @param rowHeight the height of the row to add
	 */
	private void addRow(Dimension dim, int rowWidth, int rowHeight) {

		System.out.println("New Row Being Added: " + rowcount++);
		dim.width = Math.max(dim.width, rowWidth);

		if (dim.height > 0) {
			dim.height += getVgap();
		}

		dim.height += rowHeight;
	}
}