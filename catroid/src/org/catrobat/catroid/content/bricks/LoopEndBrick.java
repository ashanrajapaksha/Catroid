/**
 *  Catroid: An on-device visual programming system for Android devices
 *  Copyright (C) 2010-2013 The Catrobat Team
 *  (<http://developer.catrobat.org/credits>)
 *  
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Affero General Public License as
 *  published by the Free Software Foundation, either version 3 of the
 *  License, or (at your option) any later version.
 *  
 *  An additional term exception under section 7 of the GNU Affero
 *  General Public License, version 3, is available at
 *  http://developer.catrobat.org/license_additional_term
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU Affero General Public License for more details.
 *  
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.catrobat.catroid.content.bricks;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;

import org.catrobat.catroid.R;
import org.catrobat.catroid.content.Script;
import org.catrobat.catroid.content.Sprite;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class LoopEndBrick extends NestingBrick implements AllowedAfterDeadEndBrick {
	static final int FOREVER = -1;
	private static final long serialVersionUID = 1L;
	private static final String TAG = LoopEndBrick.class.getSimpleName();
	private LoopBeginBrick loopBeginBrick;

	public LoopEndBrick(Sprite sprite, LoopBeginBrick loopStartingBrick) {
		this.sprite = sprite;
		this.loopBeginBrick = loopStartingBrick;
		loopStartingBrick.setLoopEndBrick(this);
	}

	public LoopEndBrick() {

	}

	@Override
	public int getRequiredResources() {
		return NO_RESOURCES;
	}

	@Override
	public Brick copyBrickForSprite(Sprite sprite, Script script) {
		LoopEndBrick copyBrick = (LoopEndBrick) clone();
		loopBeginBrick.setLoopEndBrick(this);

		copyBrick.sprite = sprite;
		return copyBrick;
	}

	public LoopBeginBrick getLoopBeginBrick() {
		return loopBeginBrick;
	}

	public void setLoopBeginBrick(LoopBeginBrick loopBeginBrick) {
		this.loopBeginBrick = loopBeginBrick;
	}

	@Override
	public View getView(Context context, int brickId, BaseAdapter baseAdapter) {
		if (animationState) {
			return view;
		}

		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.brick_loop_end, null);
			view = getViewWithAlpha(alphaValue);
			checkbox = (CheckBox) view.findViewById(R.id.brick_loop_end_checkbox);

			setCheckboxView(R.id.brick_loop_end_checkbox);
			final Brick brickInstance = this;

			checkbox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					checked = isChecked;
					adapter.handleCheck(brickInstance, isChecked);
				}
			});
		}

		return view;
	}

	@Override
	public View getViewWithAlpha(int alphaValue) {

		if (view != null) {

			View layout = view.findViewById(R.id.brick_loop_end_layout);
			if (layout == null) {
				layout = view.findViewById(R.id.brick_loop_end_no_puzzle_layout);
				TextView loopLabel = (TextView) view.findViewById(R.id.brick_loop_end_no_puzzle_label);
				loopLabel.setTextColor(loopLabel.getTextColors().withAlpha(alphaValue));
			} else {
				TextView loopLabel = (TextView) view.findViewById(R.id.brick_loop_end_label);
				loopLabel.setTextColor(loopLabel.getTextColors().withAlpha(alphaValue));
			}
			Drawable background = layout.getBackground();
			background.setAlpha(alphaValue);

			this.alphaValue = (alphaValue);

		}

		return view;
	}

	@Override
	public Brick clone() {
		return new LoopEndBrick(getSprite(), getLoopBeginBrick());
	}

	@Override
	public View getPrototypeView(Context context) {
		return View.inflate(context, R.layout.brick_loop_end, null);
	}

	@Override
	public boolean isDraggableOver(Brick brick) {
		if (brick == loopBeginBrick) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public boolean isInitialized() {
		if (loopBeginBrick == null) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public void initialize() {
		loopBeginBrick = new ForeverBrick(sprite);
		Log.w(TAG, "Not supposed to create the LoopBeginBrick!");
	}

	@Override
	public List<NestingBrick> getAllNestingBrickParts(boolean sorted) {
		List<NestingBrick> nestingBrickList = new ArrayList<NestingBrick>();
		if (sorted) {
			nestingBrickList.add(loopBeginBrick);
			nestingBrickList.add(this);
		} else {
			nestingBrickList.add(this);
			nestingBrickList.add(loopBeginBrick);
		}
		return nestingBrickList;
	}

	@Override
	public View getNoPuzzleView(Context context, int brickId, BaseAdapter baseAdapter) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		return inflater.inflate(R.layout.brick_loop_end_no_puzzle, null);
	}

	@Override
	public List<SequenceAction> addActionToSequence(SequenceAction sequence) {
		LinkedList<SequenceAction> returnActionList = new LinkedList<SequenceAction>();
		returnActionList.add(sequence);
		return returnActionList;
	}
}
