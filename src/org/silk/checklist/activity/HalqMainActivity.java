package org.silk.checklist.activity;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.silk.checklist.App;
import org.silk.checklist.R;
import org.silk.checklist.bo.AnswersheetBo;
import org.silk.checklist.bo.AuditorBo;
import org.silk.checklist.bo.BPartnerBo;
import org.silk.checklist.bo.PaperBo;
import org.silk.checklist.bo.QuestionBo;
import org.silk.checklist.db.DbHelper;
import org.silk.checklist.model.Answersheet;
import org.silk.checklist.model.Auditor;
import org.silk.checklist.model.BPartner;
import org.silk.checklist.model.Choice;
import org.silk.checklist.model.Option;
import org.silk.checklist.model.Paper;
import org.silk.checklist.model.Question;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

public class HalqMainActivity extends Activity {
	App app;
	// Progress dialog type (0 - for Horizontal progress bar)
	public static final int progress_bar_type = 0;
	// Progress Dialog
	private ProgressDialog pDialog;
	private int progressBarStatus = 0;
	private Handler progressBarHandler = new Handler();
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		app = (App)getApplication();
		if(app.isNeedUpdate()){
			System.out.println("need update...");
			new DBUpdator().execute("hello");
		}
		setContentView(R.layout.halq_main);

	}
	
	public void onBtnStartClick(View view){
		startActivity(new Intent(HalqMainActivity.this, HalqMainMenu.class));
	}
	/**
	 * Showing Dialog
	 * */
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case progress_bar_type:
			pDialog = new ProgressDialog(this);
			pDialog.setMessage("Downloading file. Please wait...");
			pDialog.setIndeterminate(false);
			pDialog.setMax(100);
			pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			pDialog.setCancelable(true);
			pDialog.show();
			return pDialog;
		default:
			return null;
		}
	}
	private void updateProgressBar() {
		// Update the progress bar
		progressBarHandler.post(new Runnable() {
			public void run() {
				pDialog.setProgress(progressBarStatus);
			}
		});
	}
	public class DBUpdator extends AsyncTask<String, String, String>{

		 
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			showDialog(progress_bar_type);
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			progressBarStatus = 0;
			loadSampleData();
			return null;
		}
		/**
		 * After completing background task
		 * Dismiss the progress dialog
		 * **/
		@Override
		protected void onPostExecute(String file_url) {
			// dismiss the dialog after the file was downloaded
			dismissDialog(progress_bar_type);
			
			
			
		}
		private void loadSampleData() {

			DbHelper dbHelper = app.getDbHelper();
			dbHelper.onUpgrade(dbHelper.getWritableDatabase(),
					dbHelper.DATABASE_VERSION, dbHelper.DATABASE_VERSION + 1);

			createQuestion();
			createpaper();
			createBPartner();
			createAnswersheet();
			createAuditor();
		}
		private void createQuestion() {

			Question q1 = new Question(1,
					"4.1 มีคู่มือคุณภาพ (QM) ครอบคลุมการผลิตอาหารฮาลาล");
			q1.setGroupName("4. ระบบการบริหารด้านฮาลาล");
			q1.addChoice(new Choice(1, "มี"));
			q1.addChoice(new Choice(2, "ไม่มี"));

			Question q2 = new Question(2,
					"4.2 มีคู่มือการปฏิบัติงานครอบคลุมการผลิตอาหารฮาลาล");
			q2.setGroupName("4. ระบบการบริหารด้านฮาลาล");
			q2.addChoice(new Choice(3, "มี"));
			q2.addChoice(new Choice(4, "ไม่มี"));

			Question q3 = new Question(3,
					"4.3 มีการควบคุมเอกสารและบันทึกการปฏิบัติในการผลิตอาหารฮาลาล");
			q3.setGroupName("4. ระบบการบริหารด้านฮาลาล");
			q3.addChoice(new Choice(5, "มี"));
			q3.addChoice(new Choice(6, "ไม่มี"));
			q3.getChoice(6).addOption(new Option(1, "ไม่มีการใช้งาน"));
			q3.getChoice(6).addOption(new Option(2, "ไม่มีระบบการเก็บ"));
			q3.getChoice(6).addOption(new Option(3, "ไม่มีระบบการทำลาย"));

			Question q4 = new Question(4,
					"4.4 มีการควบคุมเอกสารและบันทึกการปฏิบัติในการผลิตอาหารฮาลาล");
			q4.setGroupName("4. ระบบการบริหารด้านฮาลาล");
			q4.addChoice(new Choice(7, "มี"));
			q4.addChoice(new Choice(8, "ไม่มี"));
			q4.getChoice(8).addOption(new Option(4, "นโยบาย"));
			q4.getChoice(8).addOption(new Option(5, "วัตถุประสงค์"));
			q4.getChoice(8).addOption(new Option(6, "ประกาศ"));

			Question q5 = new Question(5,
					"5.1 มีการจัดสรรทรัพยาการในการผลิตอาหารฮาลาลอย่างเหมาะสม และเพียงพอ");
			q5.setGroupName("5. การบริหารทรัพยากร");
			q5.addChoice(new Choice(9, "มี"));
			q5.addChoice(new Choice(10, "ไม่มี"));
			q5.getChoice(10).addOption(new Option(7, "บุคลากรไม่เพียงพอ"));
			q5.getChoice(10).addOption(new Option(8, "สิ่งอำนวยความสะดวกไม่พร้อม"));
			q5.getChoice(10).addOption(new Option(9, "เงินทุนไม่เพียงพอ"));

			
			Question q6 = new Question(6,"วัตถุดิบที่ใช้");
			q6.setGroupName("1. เอกสาร");
			q6.addChoice(new Choice(11, "ถูกต้อง"));
			q6.addChoice(new Choice(12, "ไม่ถูกต้อง"));
			
			Question q7 = new Question(7,"ส่วนผสม");
			q7.setGroupName("1. เอกสาร");
			q7.addChoice(new Choice(13, "ถูกต้อง"));
			q7.addChoice(new Choice(14, "ไม่ถูกต้อง"));
			
			Question q8 = new Question(8,"ภายนอกบริเวณรอบอาคารการผลิต");
			q8.setGroupName("2. สถานที่ผลิต");
			q8.addChoice(new Choice(15, "ดี"));
			q8.addChoice(new Choice(16, "ปานกลาง"));
			q8.addChoice(new Choice(17, "ต้องปรับปรุง"));
			
			
			Question q9 = new Question(9,"ภายในบริเวณอาคารการผลิต");
			q9.setGroupName("2. สถานที่ผลิต");
			q9.addChoice(new Choice(18, "ดี"));
			q9.addChoice(new Choice(19, "ปานกลาง"));
			q9.addChoice(new Choice(20, "ต้องปรับปรุง"));
			
			
			List<Question> questionList = new ArrayList<Question>();
			questionList.add(q1);
			questionList.add(q2);
			questionList.add(q3);
			questionList.add(q4);
			questionList.add(q5);
			
			questionList.add(q6);
			questionList.add(q7);
			questionList.add(q8);
			questionList.add(q9);
			
			QuestionBo questionBo = new QuestionBo(app.getDatabase());

			questionBo.create(questionList);
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			progressBarStatus += 20;
			updateProgressBar();

		}

		public void createpaper() {

			Set<Integer> qSet1 = new LinkedHashSet<Integer>();
			qSet1.add(1);
			qSet1.add(2);
			qSet1.add(3);
			qSet1.add(5);
			Set<Integer> qSet2 = new LinkedHashSet<Integer>();
			qSet2.add(1);
			qSet2.add(2);
			qSet2.add(4);
			Set<Integer> qSet3 = new LinkedHashSet<Integer>();
			qSet3.add(1);
			qSet3.add(2);
			qSet3.add(3);
			
			Set<Integer> qSet4 = new LinkedHashSet<Integer>();
			qSet4.add(6);
			qSet4.add(7);
			qSet4.add(8);
			qSet4.add(9);
			
			Paper p1 = new Paper(1, "แบบประเมิน HAL-Q [มาตรฐาน]");
			p1.setQuestions(qSet1);
			Paper p2 = new Paper(2, "แบบประเมิน HAL-Q [โรงงาน]");
			p2.setQuestions(qSet2);
			Paper p3 = new Paper(3, "แบบประเมิน HAL-Q [โรงเชือด]");
			p3.setQuestions(qSet3);
			
			Paper p4 = new Paper(4, "แบบประเมิน HALAL [มาตรฐาน]");
			p4.setQuestions(qSet4);

			List<Paper> papers = new ArrayList<Paper>();
			papers.add(p1);
			papers.add(p2);
			papers.add(p3);
			papers.add(p4);

			PaperBo paperBo = new PaperBo(app.getDatabase());

			paperBo.create(papers);
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			progressBarStatus += 20;
			updateProgressBar();

		}

		public void createAnswersheet() {

			AnswersheetBo answersheetBo = new AnswersheetBo(app.getDatabase());
			BPartnerBo bpbo = new BPartnerBo(app.getDatabase());
			PaperBo paperBo = new PaperBo(app.getDatabase());

			Paper paper = paperBo.getPaper(2);
			BPartner bp = bpbo.getBPartner(1);
			Answersheet answersheet = new Answersheet();
			answersheet.setAnswersheetName("รายการตรวจประเมิน HAL-Q บริษัท TIPCO");
			answersheet.setPaper(paper);
			answersheet.setBpartner(bp);
			answersheetBo.save(answersheet);
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			progressBarStatus += 20;
			updateProgressBar();

		}

		public void createBPartner() {

			BPartnerBo bpbo = new BPartnerBo(app.getDatabase());

			BPartner bp1 = new BPartner(1, "HI-Q", "123/456 บางกอกน้อย กรุงเทพฯ",
					"123/456 บางกอกน้อย กรุงเทพฯ", 10, 5, 1);
			BPartner bp2 = new BPartner(2, "ยำแซ่บ", "456 บางเชือกหนัง กรุงเทพฯ",
					"789 ตะลุเตา พัทลุง", 100, 2, 70);
			List<BPartner> bpList = new ArrayList<BPartner>();
			bpList.add(bp1);
			bpList.add(bp2);
			bpbo.create(bpList);
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			progressBarStatus += 20;
			updateProgressBar();

		}

		public void createAuditor() {

			AuditorBo auditorBo = new AuditorBo(app.getDatabase());

			List<Auditor> list = new ArrayList<Auditor>();
			list.add(new Auditor(1, "นายอาณัฐ เด่นยิ่งโยชน์"));
			list.add(new Auditor(2, "นายธรรมนูญ ดีสวัสดิ์"));
			list.add(new Auditor(3, "นางสาววริวรรณ เครืออินทร์"));
			auditorBo.save(list);
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			progressBarStatus += 20;
			updateProgressBar();

		}
		
	}
}
