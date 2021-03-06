package bto.android;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

public class PaceCalcKilo extends Activity {
	private EditText text1a;
	private EditText text1b;
	private EditText text1c;
	private EditText text2;
	private EditText text3a;
	private EditText text3b;
	private EditText text3c;
	private Spinner s;
	private Dialog dialog;
	private ListView lv;
	private PackageInfo pInfo = null;
	private Button switchButton;
	private Button clearButton;
	private Button timeButton;
	private Button distanceButton;
	private Button paceButton;
	private TextView title;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_kilo); // bind the layout to the activity
		title = (TextView) findViewById(R.id.strapkm);
		text1a = (EditText) findViewById(R.id.EditText01akm);
		text1b = (EditText) findViewById(R.id.EditText01bkm);
		text1c = (EditText) findViewById(R.id.EditText01ckm);
		text2 = (EditText) findViewById(R.id.EditText02km);
		text3a = (EditText) findViewById(R.id.EditText03akm);
		text3b = (EditText) findViewById(R.id.EditText03bkm);
		text3c = (EditText) findViewById(R.id.EditText03ckm);
		text1a.setWidth(10);
		text1b.setWidth(10);
		text1c.setWidth(10);
		lv = (ListView) findViewById(R.id.ListViewMetric);
		clearButton = (Button) findViewById(R.id.ClearButtonkm);
		clearButton.setTextColor(getResources().getColor(R.color.darkGrey));
		switchButton = (Button) findViewById(R.id.SwitchMetricskm);
		switchButton.setTextColor(getResources().getColor(R.color.darkGrey));
		timeButton = (Button) findViewById(R.id.Button01km);
		distanceButton = (Button) findViewById(R.id.Button02km);
		paceButton = (Button) findViewById(R.id.Button03km);

		s = (Spinner) findViewById(R.id.SpinnerMetric);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
				R.array.metric, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		s.setAdapter(adapter);
		s.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int selectedPosition, long arg3) {

				if (selectedPosition == 0) {
					text2.setText("");
					lv.setAdapter(null);
				} else {
					String str = getPresetDistance(selectedPosition);
					text2.setText(str);
					distanceButton.setEnabled(false);
					text2.setFocusable(true);
					text2.setFocusableInTouchMode(true);
					text2.requestFocus();
				}
			}

			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});

		switchButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent switchMetric = new Intent(getBaseContext(),
						PaceCalc.class);
				startActivity(switchMetric);
				PaceCalcKilo.this.finish();
			}
		});

		text1a.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					timeButton.setEnabled(false);
				} else {
					timeButton.setEnabled(true);
				}
			}
		});
		text1b.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					timeButton.setEnabled(false);
				} else {
					timeButton.setEnabled(true);
				}
			}
		});
		text1c.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					timeButton.setEnabled(false);
				} else {
					timeButton.setEnabled(true);
				}
			}
		});
		text2.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					distanceButton.setEnabled(false);
				} else {
					distanceButton.setEnabled(true);
				}
			}
		});
		text3a.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					paceButton.setEnabled(false);
				} else {
					paceButton.setEnabled(true);
				}
			}
		});
		text3b.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					paceButton.setEnabled(false);
				} else {
					paceButton.setEnabled(true);
				}
			}
		});
		text3c.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					paceButton.setEnabled(false);
				} else {
					paceButton.setEnabled(true);
				}
			}
		});

	}

	static final private int ABOUT = Menu.FIRST;

	/* Creates the menu items */
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(0, ABOUT, 0, "About PaceCalc")
				.setIcon(R.drawable.runningman48);
		return true;
	}

	/* Handles item selections */
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case ABOUT:
			try {
				doDialog();
			} catch (Exception e) {
				// TODO: handle exception
			}
			return true;
		}
		return false;
	}

	private void doDialog() {
		try {
			pInfo = getPackageManager().getPackageInfo("bto.android",
					PackageManager.GET_META_DATA);

		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		dialog = new Dialog(this);
		dialog.setContentView(R.layout.custom_dialog2);
		dialog.setTitle("Pace Calculator " + pInfo.versionName);
		TextView text = (TextView) dialog.findViewById(R.id.text);
		StringBuffer spawny = new StringBuffer();
		spawny.append("Simply enter target time and distance to get pace, ");
		spawny.append("or vary the combinations.\n\nThe mile and kilo ");
		spawny.append("splits are scrollable, but are subject to rounding ");
		spawny.append("errors so may be a tiny, tiny bit off...\n\n");
		spawny.append("This is a Spawny App\nby Iain Downie!");
		text.setText(spawny.toString());
		ImageView image = (ImageView) dialog.findViewById(R.id.image);
		image.setImageResource(R.drawable.runningman48);
		Button but = (Button) dialog.findViewById(R.id.dismissButton);
		dialog.show();
		but.setOnClickListener(new OnClickListener() {
			// @Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
	}

	public void myClickHandler(View view) {

		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		switch (view.getId()) {
		case R.id.Button01km:
			String t3a = text3a.getText().toString();
			if (t3a == null || t3a.length() == 0)
				t3a = "0";
			String t3b = text3b.getText().toString();
			// if((new Integer(t3b).intValue()>59)) { doError(); break; }
			if (t3b == null || t3b.length() == 0)
				t3b = "0";
			String t3c = text3c.getText().toString();
			// if((new Integer(t3c).intValue()>59)) { doError(); break; }
			if (t3c == null || t3c.length() == 0)
				t3c = "0";
			String d2 = text2.getText().toString();
			if (d2 == null || d2.length() == 0)
				d2 = "0";
			String time = getTime(Double.valueOf(d2), Double.valueOf(t3a), Double.valueOf(
					t3b), Double.valueOf(t3c));
			text1a.setText(time.substring(0, time.indexOf(":")));
			text1b.setText(time.substring(time.indexOf(":") + 1,
					time.lastIndexOf(":")));
			text1c.setText(time.substring(time.lastIndexOf(":") + 1));
			timeButton.setEnabled(true);
			distanceButton.setEnabled(true);
			paceButton.setEnabled(true);
			title.setFocusableInTouchMode(true);
			title.requestFocus();
			imm.hideSoftInputFromWindow(text3c.getWindowToken(), 0);
			break;
		case R.id.Button02km:
			String aaa = text1a.getText().toString();
			if (aaa == null || aaa.length() == 0)
				aaa = "0";
			String bbb = text1b.getText().toString();
			// if((new Integer(bbb).intValue()>59)) { doError(); break; }
			if (bbb == null || bbb.length() == 0)
				bbb = "0";
			String ccc = text1c.getText().toString();
			// if((new Integer(ccc).intValue()>59)) { doError(); break; }
			if (ccc == null || ccc.length() == 0)
				ccc = "0";
			String ddd = text3a.getText().toString();
			if (ddd == null || ddd.length() == 0)
				ddd = "0";
			String eee = text3b.getText().toString();
			// if((new Integer(eee).intValue()>59)) { doError(); break; }
			if (eee == null || eee.length() == 0)
				eee = "0";
			String fff = text3c.getText().toString();
			// if((new Integer(fff).intValue()>59)) { doError(); break; }
			if (fff == null || fff.length() == 0)
				fff = "0";
			String dist = getDistance(Double.valueOf(aaa), Double.valueOf(bbb),
					Double.valueOf(ccc), Double.valueOf(ddd), Double.valueOf(eee),
					Double.valueOf(fff));
			text2.setText(dist);
			timeButton.setEnabled(true);
			distanceButton.setEnabled(true);
			paceButton.setEnabled(true);
			title.setFocusableInTouchMode(true);
			title.requestFocus();
			imm.hideSoftInputFromWindow(text1c.getWindowToken(), 0);
			break;
		case R.id.Button03km:
			String t1a = text1a.getText().toString();
			if (t1a == null || t1a.length() == 0)
				t1a = "0";
			String t1b = text1b.getText().toString();
			// if((new Integer(t1b).intValue()>59)) { doError(); break; }
			if (t1b == null || t1b.length() == 0)
				t1b = "0";
			String t1c = text1c.getText().toString();
			// if((new Integer(t1c).intValue()>59)) { doError(); break; }
			if (t1c == null || t1c.length() == 0)
				t1c = "0";
			String d3 = text2.getText().toString();
			if (d3 == null || d3.length() == 0)
				d3 = "0";
			String pace = getPace(Double.valueOf(d3), Double.valueOf(t1a), Double.valueOf(
					t1b), Double.valueOf(t1c));
			text3a.setText(pace.substring(0, pace.indexOf(":")));
			text3b.setText(pace.substring(pace.indexOf(":") + 1,
					pace.lastIndexOf(":")));
			text3c.setText(pace.substring(pace.lastIndexOf(":") + 1));
			timeButton.setEnabled(true);
			distanceButton.setEnabled(true);
			paceButton.setEnabled(true);
			title.setFocusableInTouchMode(true);
			title.requestFocus();
			imm.hideSoftInputFromWindow(text2.getWindowToken(), 0);
			break;
		case R.id.ClearButtonkm:
			text1a.setText("");
			text1b.setText("");
			text1c.setText("");
			text2.setText("");
			s.setSelection(0);
			text3a.setText("");
			text3b.setText("");
			text3c.setText("");
			lv = (ListView) findViewById(R.id.ListViewMetric);
			lv.setAdapter(null);
			timeButton.setEnabled(true);
			distanceButton.setEnabled(true);
			paceButton.setEnabled(true);
			title.setFocusableInTouchMode(true);
			title.requestFocus();
			break;
		}
	}

	public void setSplits(double dist, double total) {
		lv = (ListView) findViewById(R.id.ListViewMetric);
		ArrayList<String> results = new ArrayList<String>();
		results.add("Kilometre splits (rounded to seconds)");
		double pace = (total / dist) / 60;
		// dist = Math.round(dist / 1.609344);
		for (int i = 0; i < (int) dist; i++) {
			results.add("Km - " + (i + 1) + ": "
					+ getGoodTimeValues(pace * (i + 1)));
		}
		results.add("Last split - " + dist + ":  "
				+ getGoodTimeEndValues(total));
		String[] splits = results.toArray(new String[results.size()]);
		ListAdapter birds = (ListAdapter) (new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, splits));
		lv.setAdapter(birds);
		lv.setTextFilterEnabled(true);

	}

	private String getGoodTimeEndValues(double val) {
		val = val / 60;
		int mins = (int) val;
		double secs = val - mins;
		if (mins >= 60) {
			int hours = mins / 60;
			String str = (hours + ":" + (paddedInt((mins - (hours * 60))))
					+ ":" + paddedInt((int) Math.round(secs * 60)));
			return str;
		} else {
			return "0:" + paddedInt(mins) + ":"
					+ paddedInt((int) Math.round(secs * 60));
		}
	}

	private String getGoodTimeValues(double val) {
		int mins = (int) val;
		double secs = val - mins;
		if (mins >= 60) {
			int hours = mins / 60;
			/*
			 * String str = (hours + ":" + (paddedInt((mins - (hours * 60)))) +
			 * ":" + paddedInt((int) (secs * 60)));
			 */
			String str = (hours + ":" + (paddedInt((mins - (hours * 60))))
					+ ":" + paddedInt((int) Math.round(secs * 60)));
			return str;
		} else {
			// return "0:" + paddedInt(mins) + ":" + paddedInt((int) (secs *
			// 60));
			return "0:" + paddedInt(mins) + ":"
					+ paddedInt((int) Math.round(secs * 60));
		}
	}

	private String getTime(Double dist, Double hours, Double mins, Double secs) {
		double total = 0.0;
		if (hours > 0) {
			total = dist * (((hours * 60) * 60 * mins) + secs);
		} else {
			total = dist * ((60 * mins) + secs);
		}
		int tHours = (int) (total / 60 / 60);
		int tMins = (int) ((total / 60) - (tHours * 60));
		double tSecs = (double) (total - ((tHours * 60 * 60) + (tMins * 60)));
		this.setSplits(dist.doubleValue(), total);

		return paddedInt(tHours) + ":" + paddedInt(tMins) + ":" + tSecs;
	}

	private String getPace(Double dist, Double hours, Double mins, Double secs) {
		double totalSecs = 0.0;
		if (hours > 0) {
			totalSecs = (hours * 60 * 60) + (60 * mins) + secs;
		} else {
			totalSecs = (60 * mins) + secs;
		}
		double total = totalSecs / dist;
		int tHours = (int) (total / 60 / 60);
		int tMins = (int) ((total / 60) - (tHours * 60));
		Double tSecs = Double.valueOf((total - ((tHours * 60 * 60) + (tMins * 60))));
		if (!tSecs.isNaN()) {
			// if(dist!=null)
			this.setSplits(dist.doubleValue(), totalSecs);
			return paddedInt(tHours) + ":" + paddedInt(tMins) + ":"
					+ tSecs.doubleValue();
		} else {
			return "00:00:0.0";
		}
	}

	/*private String convertPace(String hours, String mins, String secs) {
		double totalSecs = 0.0;
		if (hours == null || hours.equals("")) {
			hours = "0";
		}
		if (mins == null || mins.equals("")) {
			mins = "0";

		}
		if (secs == null || secs.equals("")) {
			secs = "0";
		}
		Double dHours = new Double(hours);
		Double dMins = new Double(mins);
		Double dSecs = new Double(secs);
		if (dHours > 0) {
			totalSecs = (dHours * 60 * 60) + (60 * dMins) + dSecs;
		} else {
			totalSecs = (60 * dMins) + dSecs;
		}
		double convertedSecs = totalSecs / 1.609344;
		int tHours = (int) (convertedSecs / 60 / 60);
		int tMins = (int) ((convertedSecs / 60) - (tHours * 60));
		double tSecs = (double) (convertedSecs - ((tHours * 60 * 60) + (tMins * 60)));
		return (paddedInt(tHours) + ":" + paddedInt(tMins) + ":" + tSecs);
	}*/

	private String getDistance(Double hours1, Double mins1, Double secs1,
			Double hours2, Double mins2, Double secs2) {
		double totalSecs1 = 0.0;
		if (hours1 > 0) {
			totalSecs1 = (hours1 * 60 * 60) + (60 * mins1) + secs1;
		} else {
			totalSecs1 = (60 * mins1) + secs1;
		}
		double totalSecs2 = 0.0;
		if (hours2 > 0) {
			totalSecs2 = (hours2 * 60 * 60) + (60 * mins2) + secs2;
		} else {
			totalSecs2 = (60 * mins2) + secs2;
		}
		if (totalSecs1 > 0.0 && totalSecs2 > 0.0) {
			// New code to calculate splits
			this.setSplits(totalSecs1 / totalSecs2, totalSecs1);
			return "" + totalSecs1 / totalSecs2;
		} else
			return "0.0";
	}

	private String paddedInt(int val) {
		if (val < 10)
			return "0" + val;
		else
			return "" + val;
	}

	private String getPresetDistance(int preset) {
		if (preset == 1)
			return "42.195";
		else if (preset == 2)
			return "30";
		else if (preset == 3)
			return "21.1";
		else if (preset == 4)
			return "10";
		else if (preset == 5)
			return "5";
		else
			return "";
	}
}