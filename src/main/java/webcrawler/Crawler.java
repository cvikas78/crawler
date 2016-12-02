package webcrawler;

import java.io.IOException;
import java.util.HashSet;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Crawler {
	static HashSet<String> hSet = new HashSet<String>();

	public static void main(String[] args) throws InterruptedException {
	/*
	 * USe setProxy() if you are behind proxy.
	 */
		setProxy();
		crawl("http://wiprodigital.com/");
	}

	public static void crawl(String url) {
		try {
			Document doc = Jsoup.connect(url).timeout(0).get();
			
			/* Look for all navigation links */
			Elements links = doc.select("a.wd-navbar-nav-elem-link");
			
			/* iterate through links, print the link only if it is not already navigated */
			for (Element link : links) {
				if (link.attr("href").contains("wiprodigital.com") && !hSet.contains(link.attr("abs:href"))) {
					hSet.add(link.attr("abs:href"));
					System.out.println(link.attr("abs:href"));
					crawl(link.attr("abs:href"));
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public static void setProxy() {
		System.setProperty("http.proxyHost", "103.27.171.32");
		System.setProperty("http.proxyPort", "80");
	}
}
