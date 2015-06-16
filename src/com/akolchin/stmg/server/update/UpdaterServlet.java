package com.akolchin.stmg.server.update;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.akolchin.stmg.server.dao.StockDao;
import com.akolchin.stmg.shared.TooManyResultsException;
import com.akolchin.stmg.shared.domain.Stock;

@SuppressWarnings("serial")
public class UpdaterServlet extends HttpServlet {
	private static final Logger log = Logger.getLogger(UpdaterServlet.class.getCanonicalName());

	private static final String SYMBOL = "Symbol"; // s
	private static final String NAME = "Name"; // n
	private static final String LAST_TRADE_DATE = "Last Trade Date"; // d1
	private static final String LAST_TRADE_TIME = "Last Trade Time"; // t1
	private static final String LAST_TRADE = "Last Trade (Price Only)"; // l1

	private static final String CHANGE = "Change"; // c1
	private static final String CHANGE_IN_PERCENT = "Change in Percent"; // p2

	private static final String BID = "Bid"; // b
	private static final String ASK = "Ask"; // a

	private static final String RT_LAST_TRADE = "Last Trade (Real-time) with Time"; // k1
	private static final String RT_BID = "Bid (Real-time)"; // b3
	private static final String RT_ASK = "Ask (Real-time)"; // b2
	private static final String RT_CHANGE_IN_PERCENT = "Change Percent (Real-time)"; // k2
	private static final String RT_CHANGE = "Change (Real-time)"; // c6

	private static final String PREV_CLOSE = "Previous Close"; // p
	private static final String OPEN = "Open"; // o

	private static final String BASE_URL = "http://download.finance.yahoo.com/d/quotes.csv";
	private static final String SYMBOLS = "MSFT,GOOG,MBT,VZ,T,ABEV,GIS,HPQ,IBM,KEY,NLY,ORCL";
	private static final String FORMAT = "snd1t1l1c1p2bak1b3b2k2c6po";
	private static final String URL = BASE_URL + "?s=" + SYMBOLS + "&f=" + FORMAT;

	// "3/24/2014 11:53am"
	private static final DateTimeFormatter myFormater = DateTimeFormat.forPattern("M/d/y h:ma");

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		log.log(Level.FINER, "Entered in doGet");

		// request URL and parse result
		URL url = new URL(URL);
		Iterable<CSVRecord> records = CSVParser.parse(url, null, CSVFormat.EXCEL.withHeader(SYMBOL, NAME,
				LAST_TRADE_DATE, LAST_TRADE_TIME, LAST_TRADE, CHANGE, CHANGE_IN_PERCENT, BID, ASK, RT_LAST_TRADE,
				RT_BID, RT_ASK, RT_CHANGE_IN_PERCENT, RT_CHANGE, PREV_CLOSE, OPEN));

		StockDao dao = new StockDao();
		for (CSVRecord record : records) {

			// update stock
			String symbol = record.get(SYMBOL);
			Stock stock = null;
			try {
				stock = dao.getBySymbol(symbol);
			} catch (TooManyResultsException e) {
				// TODO understand how I have deal with such error
				log.log(Level.WARNING, "duplicated symbol record ", e);
				throw new ServletException("duplicated symbol record ", e);
			}

			if (stock == null) {
				stock = new Stock();
				stock.setSymbol(symbol);
			}

			stock.setName(record.get(NAME));
			// stock.setLastTradeDateTime(LocalDateTime.parse(
			// record.get(LAST_TRADE_DATE) + " " + record.get(LAST_TRADE_TIME), myFormater));
			stock.setLastTradePrice(Double.valueOf(record.get(LAST_TRADE)));
			stock.setPriceChange(Double.valueOf(record.get(CHANGE)));

			String change_in_percent = record.get(CHANGE_IN_PERCENT);
			stock.setPriceChangeInPercent(Double.valueOf(change_in_percent.replace('%', ' ')));

			stock = dao.saveAndReturn(stock);

			log.log(Level.FINEST, "new stock quote record " + record.toString() + " parsed as " + stock.toString());

		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		log.log(Level.FINER, "Entered in doGet");
	}

}
