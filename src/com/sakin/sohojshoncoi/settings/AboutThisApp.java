package com.sakin.sohojshoncoi.settings;

import com.sakin.sohojshoncoi.R;
import com.sakin.sohojshoncoi.Utils;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

public class AboutThisApp extends Fragment {
	
	View rootView = null;
	private WebView webView;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.about_this_app, container, false);
		
		TextView tv = (TextView) rootView.findViewById(R.id.about_app1);
		tv.setTypeface(Utils.banglaTypeFaceSutonny);
		tv.setText("mnR-mÂq e¨w³MZ Avq-e¨‡qi wnmve msi¶‡Yi Rb¨ GKwU Abb¨ Gwc­‡Kkb| ïaygvÎ wnmve msi¶YB bq, Avcbvi ˆ`bw›`b RxebhvÎvi Dbœq‡bi j‡¶¨I GLv‡b cv‡eb wewfbœ w`K-wb‡`©kbv Ges civgk©| Gwc­‡KkbwUi †h †Kvb †cBR G evg †_‡K Wv‡b WªvM Ki‡j A_ev Dc‡ii mnR-mÂq AvBK‡b wK¬K Ki‡j Avcwb AwZwiw³ †gbyevi †`L‡Z cv‡eb| GLv‡b e¨w³MZ Avq e„w×i Rb¨ mÂq e„w×i †KŠkj †mKk‡b wKQz wfwWI ms‡hvRb Kiv n‡q‡Q| GQvovI wewfbœ Av‡qi †KŠkj †mKk‡b Av‡qi wewfbœ gva¨g m¤^wjZ w`K wb‡`©kbvg~jK wfwWI †`qv n‡q‡Q| mdj‡`i mvdj¨Mv_v †mKk‡b wewfbœ †¶‡Î mdj e¨w³‡`i cwikªgx Rxeb Ges mvd‡j¨i †cQ‡bi Mí m¤^wjZ wfwWI ms‡hvRb Kiv n‡q‡Q| GQvovI †h †Kvb †mKk‡b Avcwb Avcbvi cQ›` Abyhvqx †Kvb wfwWI ms‡hvRb Ki‡Z PvB‡j A_ev Avcbvi mvd‡j¨i K_v Avgv‡`i Rvbv‡Z PvB‡j wb‡¤èv³ wVKvbvq Avcbvi wfwWI B-‡gBj Ki“b");
		
		TextView tv1 = (TextView) rootView.findViewById(R.id.about_mail);
		tv1.setText("sohoj.sonchoy@gmail.com");
		
		TextView tv2 = (TextView) rootView.findViewById(R.id.about_app2);
		tv2.setTypeface(Utils.banglaTypeFaceSutonny);
		tv2.setText("GQvovI †h †Kvb civg‡k©i Rb¨ A_ev Avcbvi e¨w³MZ AwfÁZv †kqvi Kivi Rb¨ GLv‡b i‡q‡Q GKwU †WwW‡K‡UW †dvivg| wRÁvmv †mKkbwU †dBmeyK wbf©i GKwU †cBR †hLv‡b Avcwb †h †Kvb wKQz wRÁvmv A_ev Avcbvi gyj¨evb gZvgZ w`‡Z cvi‡eb| wet`ªt G‡¶‡Î Avcbvi Aek¨B GKwU †dBmeyK GKvD›U _vK‡Z n‡e|");
		Utils.setActionBarTitle(getActivity(), "mnR mÂq");
		
		return rootView;
	}
}
