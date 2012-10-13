package org.hsc.silk.halal.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.hsc.silk.halal.R;
import org.hsc.silk.adapter.ImgBtnAdapter;
import org.hsc.silk.db.DbHelper;
import org.hsc.silk.App;
import org.hsc.silk.halq.bo.AnswersheetBo;
import org.hsc.silk.halq.bo.AuditorBo;
import org.hsc.silk.halq.bo.BPartnerBo;
import org.hsc.silk.halq.bo.PaperBo;
import org.hsc.silk.halq.bo.QuestionBo;
import org.hsc.silk.model.Answersheet;
import org.hsc.silk.model.Auditor;
import org.hsc.silk.model.BPartner;
import org.hsc.silk.model.Choice;
import org.hsc.silk.model.Option;
import org.hsc.silk.model.Paper;
import org.hsc.silk.model.Question;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;



public class HalqMainMenu extends Activity {
	Resources res;
	public String tag = this.getClass().getName();

	GridView gridView;

	static final int MENU0 = 0;
	static final int MENU1 = 1;
	static final int MENU2 = 2;
	static final int MENU3 = 3;
	MenuButton[] menuButton;

	private void initMenu() {
		menuButton = new MenuButton[1];
//		menuButton[0] = new MenuButton(MENU0, res.getString(R.string.menu_0),
//				R.drawable.ic_paper);
		menuButton[0] = new MenuButton(MENU1, res.getString(R.string.menu_1),
				R.drawable.ic_answersheet);
//		menuButton[1] = new MenuButton(MENU2, res.getString(R.string.menu_2),
//				R.drawable.ic_download);
//		menuButton[3] = new MenuButton(MENU3, res.getString(R.string.menu_3),
//				R.drawable.ic_sample);
		

	}

	
	

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		res = getResources();
		initMenu();

		setContentView(R.layout.halq_main_menu);
		setTitle(R.string.title_activity_main_menu);

		gridView = (GridView) findViewById(R.id.gridView1);
		

		gridView.setAdapter(new ImgBtnAdapter(this, menuButton));

		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				// TODO Auto-generated method stub
				int menuId = (int) id;
				switch (menuId) {
				case MENU0:
					viewPaperList();
					break;
				case MENU1:
					viewAnswerSheetList();
					break;
				case MENU2:
					// loadData();
					load(v);
					break;
				case MENU3:
					// loadSampleData();
					load(v);
				}
			}
		});

	}

	ProgressDialog progressBar;
	private int progressBarStatus = 0;
	private Handler progressBarHandler = new Handler();



	private void load(View v) {
		
		// prepare for a progress bar dialog
		progressBar = new ProgressDialog(v.getContext());
		progressBar.setCancelable(true);
		progressBar.setMessage("loading ...");
		progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		progressBar.setProgress(0);
		progressBar.setMax(100);
		progressBar.show();

		// reset progress bar status
		progressBarStatus = 0;



		new Thread(new Runnable() {
			public void run() {
//				loadSampleData();
				// ok, file is downloaded,
				if (progressBarStatus >= 100) {
					// sleep 2 seconds, so that you can see the 100%
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					// close the progress bar dialog
					progressBar.dismiss();
				}
			}
		}).start();
	}

//	private void loadSampleData() {
//
//		DbHelper dbHelper = App.getDbHelper();
//		dbHelper.onUpgrade(dbHelper.getWritableDatabase(),
//				dbHelper.DATABASE_VERSION, dbHelper.DATABASE_VERSION + 1);
//
//		createQuestion();
//		createpaper();
//		createBPartner();
//		createAnswersheet();
//		createAuditor();
//	}

	private void viewPaperList() {

		Intent intent = new Intent(this, PaperListActivity.class);
		startActivity(intent);
	}

	private void viewAnswerSheetList() {
		Intent intent = new Intent(this, AnswersheetListActivity.class);
		startActivity(intent);
	}



	private void message(String msg) {
		Toast toast;
		toast = Toast.makeText(this, msg, Toast.LENGTH_LONG);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}

	private void updateProgressBar() {
		// Update the progress bar
		progressBarHandler.post(new Runnable() {
			public void run() {
				progressBar.setProgress(progressBarStatus);
			}
		});
	}

//	private void createQuestion() {
//
//		Question q1 = new Question(1,
//				"4.1 มีคู่มือคุณภาพ (QM) ครอบคลุมการผลิตอาหารฮาลาล");
//		q1.setGroupName("4. ระบบการบริหารด้านฮาลาล");
//		q1.addChoice(new Choice(1, "มี"));
//		q1.addChoice(new Choice(2, "ไม่มี"));
//
//		Question q2 = new Question(2,
//				"4.2 มีคู่มือการปฏิบัติงานครอบคลุมการผลิตอาหารฮาลาล");
//		q2.setGroupName("4. ระบบการบริหารด้านฮาลาล");
//		q2.addChoice(new Choice(3, "มี"));
//		q2.addChoice(new Choice(4, "ไม่มี"));
//
//		Question q3 = new Question(3,
//				"4.3 มีการควบคุมเอกสารและบันทึกการปฏิบัติในการผลิตอาหารฮาลาล");
//		q3.setGroupName("4. ระบบการบริหารด้านฮาลาล");
//		q3.addChoice(new Choice(5, "มี"));
//		q3.addChoice(new Choice(6, "ไม่มี"));
//		q3.getChoice(6).addOption(new Option(1, "ไม่มีการใช้งาน"));
//		q3.getChoice(6).addOption(new Option(2, "ไม่มีระบบการเก็บ"));
//		q3.getChoice(6).addOption(new Option(3, "ไม่มีระบบการทำลาย"));
//
//		Question q4 = new Question(4,
//				"4.4 มีการควบคุมเอกสารและบันทึกการปฏิบัติในการผลิตอาหารฮาลาล");
//		q4.setGroupName("4. ระบบการบริหารด้านฮาลาล");
//		q4.addChoice(new Choice(7, "มี"));
//		q4.addChoice(new Choice(8, "ไม่มี"));
//		q4.getChoice(8).addOption(new Option(4, "นโยบาย"));
//		q4.getChoice(8).addOption(new Option(5, "วัตถุประสงค์"));
//		q4.getChoice(8).addOption(new Option(6, "ประกาศ"));
//
//		Question q5 = new Question(5,
//				"5.1 มีการจัดสรรทรัพยาการในการผลิตอาหารฮาลาลอย่างเหมาะสม และเพียงพอ");
//		q5.setGroupName("5. การบริหารทรัพยากร");
//		q5.addChoice(new Choice(9, "มี"));
//		q5.addChoice(new Choice(10, "ไม่มี"));
//		q5.getChoice(10).addOption(new Option(7, "บุคลากรไม่เพียงพอ"));
//		q5.getChoice(10).addOption(new Option(8, "สิ่งอำนวยความสะดวกไม่พร้อม"));
//		q5.getChoice(10).addOption(new Option(9, "เงินทุนไม่เพียงพอ"));
//
//		
//		Question q6 = new Question(6,"วัตถุดิบที่ใช้");
//		q6.setGroupName("1. เอกสาร");
//		q6.addChoice(new Choice(11, "ถูกต้อง"));
//		q6.addChoice(new Choice(12, "ไม่ถูกต้อง"));
//		
//		Question q7 = new Question(7,"ส่วนผสม");
//		q7.setGroupName("1. เอกสาร");
//		q7.addChoice(new Choice(13, "ถูกต้อง"));
//		q7.addChoice(new Choice(14, "ไม่ถูกต้อง"));
//		
//		Question q8 = new Question(8,"ภายนอกบริเวณรอบอาคารการผลิต");
//		q8.setGroupName("2. สถานที่ผลิต");
//		q8.addChoice(new Choice(15, "ดี"));
//		q8.addChoice(new Choice(16, "ปานกลาง"));
//		q8.addChoice(new Choice(17, "ต้องปรับปรุง"));
//		
//		
//		Question q9 = new Question(9,"ภายในบริเวณอาคารการผลิต");
//		q9.setGroupName("2. สถานที่ผลิต");
//		q9.addChoice(new Choice(18, "ดี"));
//		q9.addChoice(new Choice(19, "ปานกลาง"));
//		q9.addChoice(new Choice(20, "ต้องปรับปรุง"));
//		
//		
//		List<Question> questionList = new ArrayList<Question>();
//		questionList.add(q1);
//		questionList.add(q2);
//		questionList.add(q3);
//		questionList.add(q4);
//		questionList.add(q5);
//		
//		questionList.add(q6);
//		questionList.add(q7);
//		questionList.add(q8);
//		questionList.add(q9);
//		
//		QuestionBo questionBo = new QuestionBo();
//
//		questionBo.create(questionList);
//		try {
//			Thread.sleep(500);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		progressBarStatus += 20;
//		updateProgressBar();
//
//	}
//
//	public void createpaper() {
//
//		Set<Integer> qSet1 = new LinkedHashSet<Integer>();
//		qSet1.add(1);
//		qSet1.add(2);
//		qSet1.add(3);
//		qSet1.add(5);
//		Set<Integer> qSet2 = new LinkedHashSet<Integer>();
//		qSet2.add(1);
//		qSet2.add(2);
//		qSet2.add(4);
//		Set<Integer> qSet3 = new LinkedHashSet<Integer>();
//		qSet3.add(1);
//		qSet3.add(2);
//		qSet3.add(3);
//		
//		Set<Integer> qSet4 = new LinkedHashSet<Integer>();
//		qSet4.add(6);
//		qSet4.add(7);
//		qSet4.add(8);
//		qSet4.add(9);
//		
//		Paper p1 = new Paper(1, "แบบประเมิน HAL-Q [มาตรฐาน]");
//		p1.setQuestions(qSet1);
//		Paper p2 = new Paper(2, "แบบประเมิน HAL-Q [โรงงาน]");
//		p2.setQuestions(qSet2);
//		Paper p3 = new Paper(3, "แบบประเมิน HAL-Q [โรงเชือด]");
//		p3.setQuestions(qSet3);
//		
//		Paper p4 = new Paper(4, "แบบประเมิน HALAL [มาตรฐาน]");
//		p4.setQuestions(qSet4);
//
//		List<Paper> papers = new ArrayList<Paper>();
//		papers.add(p1);
//		papers.add(p2);
//		papers.add(p3);
//		papers.add(p4);
//
//		PaperBo paperBo = new PaperBo();
//
//		paperBo.create(papers);
//		try {
//			Thread.sleep(500);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		progressBarStatus += 20;
//		updateProgressBar();
//
//	}
//
//	public void createAnswersheet() {
//
//		AnswersheetBo answersheetBo = new AnswersheetBo();
//		BPartnerBo bpbo = new BPartnerBo();
//		PaperBo paperBo = new PaperBo();
//
//		Paper paper = paperBo.getPaper(2);
//		BPartner bp = bpbo.getBPartner(1);
//		Answersheet answersheet = new Answersheet();
//		answersheet.setAnswersheetName("รายการตรวจประเมิน HAL-Q บริษัท TIPCO");
//		answersheet.setPaper(paper);
//		answersheet.setBpartner(bp);
//		answersheetBo.save(answersheet);
//		try {
//			Thread.sleep(500);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		progressBarStatus += 20;
//		updateProgressBar();
//
//	}
//
//	public void createBPartner() {
//
//		BPartnerBo bpbo = new BPartnerBo();
//
//		BPartner bp1 = new BPartner(1, "HI-Q", "123/456 บางกอกน้อย กรุงเทพฯ",
//				"123/456 บางกอกน้อย กรุงเทพฯ", 10, 5, 1);
//		BPartner bp2 = new BPartner(2, "ยำแซ่บ", "456 บางเชือกหนัง กรุงเทพฯ",
//				"789 ตะลุเตา พัทลุง", 100, 2, 70);
//		List<BPartner> bpList = new ArrayList<BPartner>();
//		bpList.add(bp1);
//		bpList.add(bp2);
//		bpbo.create(bpList);
//		try {
//			Thread.sleep(500);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		progressBarStatus += 20;
//		updateProgressBar();
//
//	}
//
//	public void createAuditor() {
//
//		AuditorBo auditorBo = new AuditorBo();
//
//		List<Auditor> list = new ArrayList<Auditor>();
//		list.add(new Auditor(1, "นายอาณัฐ เด่นยิ่งโยชน์"));
//		list.add(new Auditor(2, "นายธรรมนูญ ดีสวัสดิ์"));
//		list.add(new Auditor(3, "นางสาววริวรรณ เครืออินทร์"));
//		auditorBo.save(list);
//		try {
//			Thread.sleep(500);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		progressBarStatus += 20;
//		updateProgressBar();
//
//	}
}
