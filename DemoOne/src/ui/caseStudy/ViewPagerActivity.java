package ui.caseStudy;

import java.util.ArrayList;
import java.util.List;

import com.example.demoone.R;

import domain.businessEntity.systemManagement.User;

import android.app.Activity;
import android.app.LocalActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;

public class ViewPagerActivity extends Activity {

	List<View> listViews;

	Context context = null;
	ViewPagerActivity vpa;
	@SuppressWarnings("deprecation")
	LocalActivityManager manager = null;
	TextView tvTab1, tvTab2, tvTab3;
	TabHost tabHost = null;
	private ViewPager pager = null;
	private SharedPreferences sp_userPhone;
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.viewpager);

		// 获取前一个Activity传过来的数据
		Bundle bundle = getIntent().getExtras();
		String userPhone=bundle.getString("phone");

		//通过配置文件在Activity间传递数据
		sp_userPhone= getSharedPreferences("login_and_regist", MODE_PRIVATE);
		Editor editor = sp_userPhone.edit();
		editor.putString("userPhone", userPhone);
		editor.commit();
		
		vpa = this;
		manager = new LocalActivityManager(this, true);
		manager.dispatchCreate(savedInstanceState);

		tabHost = (TabHost) findViewById(R.id.tabhost);
		tabHost.setup();
		tabHost.setup(manager);

		context = ViewPagerActivity.this;

		pager = (ViewPager) findViewById(R.id.viewpager);

		listViews = new ArrayList<View>();

		Intent intent_main = new Intent(context, MainActivity.class);
		listViews.add(getView("MainActivity", intent_main));
		
		Intent intent_main2 = new Intent(context, TestTwoActivity.class);
		listViews.add(getView("TestTwoActivity", intent_main2));
		
		Intent intent_main3 = new Intent(context, TestThreeActivity.class);
		listViews.add(getView("TestThreeActivity", intent_main3));
		
		RelativeLayout tabIndicator1 = (RelativeLayout) LayoutInflater.from(
				this).inflate(R.layout.tabwidget, null);
		tvTab1 = (TextView) tabIndicator1.findViewById(R.id.tv_title);
		tvTab1.setText("个人信息");
		tvTab1.setTextColor(vpa.getResources().getColor(R.color.main_red));
		RelativeLayout tabIndicator2 = (RelativeLayout) LayoutInflater.from(
				this).inflate(R.layout.tabwidget, null);
		tvTab2 = (TextView) tabIndicator2.findViewById(R.id.tv_title);
		tvTab2.setText("第二个Activity");

		RelativeLayout tabIndicator3 = (RelativeLayout) LayoutInflater.from(
				this).inflate(R.layout.tabwidget, null);
		tvTab3 = (TextView) tabIndicator3.findViewById(R.id.tv_title);
		tvTab3.setText("第三个Activity");

		tabHost.addTab(tabHost.newTabSpec("A").setIndicator(tabIndicator1)
				.setContent(intent_main));
		tabHost.addTab(tabHost.newTabSpec("B").setIndicator(tabIndicator2)
				.setContent(intent_main2));
		tabHost.addTab(tabHost.newTabSpec("C").setIndicator(tabIndicator3)
				.setContent(intent_main3));

		tabHost.setOnTabChangedListener(new OnTabChangeListener() {
			@Override
			public void onTabChanged(String tabId) {
				if ("A".equals(tabId)) {
					tvTab1.setTextColor(vpa.getResources().getColor(
							R.color.main_red));
					tvTab2.setTextColor(vpa.getResources().getColor(
							R.color.black));
					tvTab3.setTextColor(vpa.getResources().getColor(
							R.color.black));
					pager.setCurrentItem(0);

				}
				if ("B".equals(tabId)) {
					tvTab1.setTextColor(vpa.getResources().getColor(
							R.color.black));
					tvTab2.setTextColor(vpa.getResources().getColor(
							R.color.main_red));
					tvTab3.setTextColor(vpa.getResources().getColor(
							R.color.black));
					pager.setCurrentItem(1);

				}
				if ("C".equals(tabId)) {
					tvTab1.setTextColor(vpa.getResources().getColor(
							R.color.black));
					tvTab2.setTextColor(vpa.getResources().getColor(
							R.color.black));
					tvTab3.setTextColor(vpa.getResources().getColor(
							R.color.main_red));
					pager.setCurrentItem(2);

				}
			}
		});

		pager.setAdapter(new MyPageAdapter(listViews));
		pager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				tabHost.setCurrentTab(position);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});

	}

	@SuppressWarnings("deprecation")
	private View getView(String id, Intent intent) {
		Log.d("EyeAndroid", "getView() called! id = " + id);
		return manager.startActivity(id, intent).getDecorView();
	}

	private class MyPageAdapter extends PagerAdapter {

		private List<View> list;

		private MyPageAdapter(List<View> list) {
			this.list = list;
		}

		@Override
		public void destroyItem(ViewGroup view, int position, Object arg2) {
			ViewPager pViewPager = ((ViewPager) view);
			pViewPager.removeView(list.get(position));
		}

		@Override
		public void finishUpdate(View arg0) {
		}

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object instantiateItem(ViewGroup view, int position) {
			ViewPager pViewPager = ((ViewPager) view);
			pViewPager.addView(list.get(position));
			return list.get(position);
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {
		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void startUpdate(View arg0) {
		}

	}

	@SuppressWarnings("deprecation")
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

}
