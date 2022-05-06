package indeedScrap;
import java.io.File;


import java.io.PrintWriter;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
public class IndeedScrapper {
	public static void main(String ars[])throws Exception {
		 
		Scanner scan = new Scanner(System.in);
	    System.out.println("Entrez keyword");
	    String keyword = scan.nextLine();
		String keyword_modified=keyword.replaceAll(" ", "+");
		PrintWriter pw = new PrintWriter(new File("C:\\Users\\salma\\OneDrive\\Bureau\\indeed1.csv"));
		StringBuilder sb = new StringBuilder();
		sb.append("Title");
		sb.append(";");
		sb.append("Competences");
		sb.append("\r\n");
		int i=0;
		while(i<=200) {
			final Document document = Jsoup.connect("https://ma.indeed.com/emplois?q="+keyword_modified+"&start="+i).get();
             for(Element row : document.select("a.tapItem.fs-unmask.result")) {
				
				String link = row.attr("abs:href");
				//System.out.println(link);
             final Document doc1 = Jsoup.connect(link).get();
				
				for (Element annonce : doc1.select("div.jobsearch-JobComponent-description.icl-u-xs-mt--md")) {
					String description = annonce.select("div.jobsearch-jobDescriptionText").text();
					System.out.println(description);
					sb.append(keyword);
					sb.append(";");
					sb.append(description);
					sb.append("\r\n");
					}
				}
             i+=10;
             Thread.sleep(500);
             }
		pw.write(sb.toString());
		pw.close();
		System.out.println("finished");
	}
}
