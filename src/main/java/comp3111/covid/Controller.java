package comp3111.covid;

import javafx.event.ActionEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.time.LocalDate;
import java.util.Calendar;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.collections.ObservableList;
import javafx.scene.input.MouseEvent;
import javafx.collections.FXCollections;
import java.util.ArrayList;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.cell.PropertyValueFactory;
import java.time.LocalDate;
import java.util.HashMap;
import java.time.ZoneId;
import java.util.Date;
import javafx.scene.control.Tab;
import java.util.ResourceBundle;
import java.net.URL;
import javafx.util.Callback;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.DateCell;

import comp3111.covid.Data;
import comp3111.covid.MyApplication;
import javafx.scene.chart.NumberAxis;
import java.time.temporal.ChronoUnit;
import javafx.scene.chart.XYChart;
import java.util.HashMap;
import javafx.util.StringConverter;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import javafx.scene.paint.Paint;
import javafx.scene.Node;
import javafx.scene.shape.SVGPath;
import java.awt.Color;

/**
 * The controller for the UI
 * @author Charles Cheng
 * UI Design
 */

public class Controller implements Initializable {

	private Date tableDateOfInterest = null;
	private LocalDate rovStartDate = null;
	private LocalDate rovEndDate = null;
	private ArrayList<String> selectedCountries = null;
	private ArrayList<String> unselectedCountries = null;
	private ArrayList<String> selectedGraphCountries = null;
	private ArrayList<String> unselectedGraphCountries = null;
	private String currentSearch = null;
	private String currentGraphSearch = null;
	private boolean check = false;
	private boolean clearSelect = false;

	private LocalDate minimumDate = LocalDate.of(2020,3,1);
	private LocalDate maximumDateGraph = LocalDate.of(2021,7,19);
	private LocalDate maximumDateGeneral = LocalDate.of(2021,7,20);
  
  /** Task A **/

	//TaskA1
	private ArrayList<String> casesTableSelectedList;
    private ArrayList<String> casesTableUnSelectedList;
    private String searchcountry = null;
    private ArrayList<String> casesGraphSelectedList;
    private ArrayList<String> casesGraphUnSelectedList;
    private LocalDate CGStartDate = null;
	private LocalDate CGEndDate = null;
    

	@FXML
	private Button casesGraphSearchClear;
	
    @FXML
    private Group casesGeoGraph;

    @FXML
    private Button casesGraphClear;

    @FXML
    private DatePicker casesGraphEndDate;

    @FXML
    private Button casesGraphGenerate;

    @FXML
    private AnchorPane casesGraphGeoPane;

    @FXML
    private BorderPane casesGraphLinePane;

    @FXML
    private ListView<String> casesGraphList;
    
    @FXML
    private TextField casesGraphSearch;

    @FXML
    private DatePicker casesGraphStartDate;

    @FXML
    private LineChart<Number, Number> casesLineGraph;

    @FXML
    private NumberAxis casesLineGraphX;

    @FXML
    private NumberAxis casesLineGraphY;

    @FXML
    private TableView<CasesInfo> casesTable;
    
    @FXML
	private Button casesTableSearchClear;

    @FXML
    private Button casesTableClear;
    
    /**
     * Clear the selection of countries 
     * @param event The event occurs when the user click the "Clear" button under the countries list in Cases Table Section
     */
    
    @FXML
    void CTSelectClear(ActionEvent event) {
    		ArrayList<String> items = (ArrayList) casesTableSelectedList.clone();
    		for (String item : items) {
    			casesTableUnSelectedList.add(item);
    			casesTableSelectedList.remove(item);
    		}
    	
    	casesTableUnSelectedList.sort(Comparator.naturalOrder());
		casesTableList.getItems().clear();
    	casesTableList.setItems(FXCollections.observableArrayList(casesTableUnSelectedList));
    	CasesTableInfo();
    }
 
    @FXML
    private DatePicker casesTableDate;
    
    @FXML
    private Text casesTableTitle;
    
    /**
     * Change the title on the case table based on the selected date
     * @param event This event occurs when the user changes the date value
     */
    
    @FXML
    void CasesTableDateSelect(ActionEvent event) {
    	LocalDate ldate = casesTableDate.getValue();
    	if (ldate == null)
    		casesTableTitle.setText("Number of Confirmed COVID-19 Cases");
    	else
    		casesTableTitle.setText("Number of Confirmed COVID-19 Cases as of " + ldate.toString());
    	
    	if (ldate.isBefore(minimumDate)) {
    		casesTableDate.setValue(minimumDate);
    	} else if (ldate.isAfter(maximumDateGeneral)) {
    		casesTableDate.setValue(maximumDateGeneral);
    	}
    }
    
    @FXML
    private Button casesTableGenerate;

    @FXML
    private ListView<String> casesTableList;
    
    /**
     * Updating the countries that has been selected in the Cases Table ListView 
     * @param event This event occurs when the user click the countries they want on the Cases Table List Section
     */
    @FXML
    void CTListSelect(MouseEvent event) {
    	String currentList = casesTableList.getSelectionModel().getSelectedItem().toString();
		
		if(casesTableSelectedList != null && casesTableSelectedList.contains(currentList)) {
			casesTableUnSelectedList.add(currentList);
			casesTableSelectedList.remove(currentList);
			casesTableUnSelectedList.sort(Comparator.naturalOrder());
		}
		else if(casesTableUnSelectedList != null && casesTableUnSelectedList.contains(currentList)) {
			casesTableSelectedList.add(currentList);
			casesTableUnSelectedList.remove(currentList);
			casesTableSelectedList.sort(Comparator.naturalOrder());
		}
		ArrayList<String> sort = new ArrayList<>();
		sort.addAll(casesTableSelectedList);
		sort.addAll(casesTableUnSelectedList);
    	
		casesTableList.getItems().clear();
		casesTableList.setItems(FXCollections.observableArrayList(sort));
    	
    }

    @FXML
    private TextField casesTableSearch;
    
    /**
     * Searching the countries name
     * @param event This event occurs when the user types in the Case Table Section's search box 
     */
    @FXML
    void CTSearchBox(ActionEvent event) {
    	searchcountry = casesTableSearch.getText();
    	
    	
    	if (searchcountry == null || searchcountry == "") {
    		ArrayList<String> merged = new ArrayList<>();
        	merged.addAll(casesTableSelectedList);
        	merged.addAll(casesTableUnSelectedList);
        	casesTableList.setItems(FXCollections.observableArrayList(merged));
    	} else {
    		ArrayList<String> newSelectedList = new ArrayList<>();
    		ArrayList<String> newNotSelectedList = new ArrayList<>();
    		searchcountry = searchcountry.toLowerCase();
    		for (String s : casesTableSelectedList) {
    			if (s.toLowerCase().contains(searchcountry))
    				newSelectedList.add(s);
    		}
    		for (String s : casesTableUnSelectedList) {
    			if (s.toLowerCase().contains(searchcountry))
    				newNotSelectedList.add(s);
    		}
    		newNotSelectedList.sort(Comparator.naturalOrder());
    		newSelectedList.sort(Comparator.naturalOrder());
    		ArrayList<String> merged = new ArrayList<>();
        	merged.addAll(newSelectedList);
        	merged.addAll(newNotSelectedList);
        	casesTableList.getItems().clear();
        	casesTableList.setItems(FXCollections.observableArrayList(merged));
        	    	}
    }
    
    /**
     * Clear the search box in the Case Table Section
     * @param event This event occurs when the user clicks "X" button in the Cases Table Section
     */
    
    @FXML
    void CTSearchClear(ActionEvent event) {
    	casesTableSearch.clear();
    	ArrayList<String> merged = new ArrayList<>();
    	merged.addAll(casesTableSelectedList);
    	merged.addAll(casesTableUnSelectedList);
    	//System.out.println("-notsearched-");
    	casesTableList.setItems(FXCollections.observableArrayList(merged));
    }
    
    /**
     * Generate the data on the Case Table Section
     * @param event This event occurs when the user clicks "Generate" button in the Cases Table Section
     */
    
    @FXML
    void generateCT(ActionEvent event) {
    	CasesTableInfo();
    }
    
    @FXML
    private TableColumn<CasesInfo, String> casesTableTotal;
    
    @FXML
    private TableColumn<CasesInfo, String> casesTableCountry;
    
    @FXML
    private TableColumn<CasesInfo, String> casesTablePer1M;
    
    /**
     * @author renacheng
     * Generate a table with data of selected countries by the user. 
     */
    
    private void CasesTableInfo() {
    	ArrayList arrayList = new ArrayList<>();
    	ObservableList<CasesInfo> info = FXCollections.observableArrayList();
    	LocalDate sd = casesTableDate.getValue();
    	Date specdate = Date.from(sd.atStartOfDay(ZoneId.systemDefault()).toInstant()); 
    	if (specdate != null) {
	    	for (String i : casesTableSelectedList) {
	    		ConfirmedCase cc;
	    		cc = DataAnalysis.data.getConfirmedCaseLocSpecific(i);
	    			
	    		CasesInfo gen = new CasesInfo(i, cc.getTotalCaseDateSpecific(specdate), cc.getTotalCasePerMillionDateSpecific(specdate));
	    		info.add(gen);
	    	}
    	casesTableCountry.setCellValueFactory(new PropertyValueFactory<>("place"));
    	casesTableTotal.setCellValueFactory(new PropertyValueFactory<>("totalCases"));
    	casesTablePer1M.setCellValueFactory(new PropertyValueFactory<>("totalCasesPerM"));
		casesTable.setItems(info);
    }
    }
    
    //Task A2
    
    @FXML
    void CasesGraphStartDate(ActionEvent event) {
    	LocalDate cgstartdate = casesGraphStartDate.getValue();
    	
    	if(cgstartdate.isBefore(minimumDate)) {
    		cgstartdate = minimumDate;
    		casesGraphStartDate.setValue(minimumDate);
    	}
    	else if(cgstartdate.isAfter(maximumDateGraph)) {
    		cgstartdate = maximumDateGraph;
    		casesGraphStartDate.setValue(maximumDateGraph);
    	}
    	CGStartDate = cgstartdate;
    	
         CasesGraphEndDate(event);
    }
    
    @FXML
    void CasesGraphEndDate(ActionEvent event) {
    	LocalDate cgenddate = casesGraphEndDate.getValue();
    	
    	 if(!cgenddate.isAfter(CGStartDate)) {
    		cgenddate = CGStartDate.plusDays(1);
    		casesGraphEndDate.setValue(cgenddate);
    	}
    	else if(cgenddate.isAfter(maximumDateGeneral)) {
    		cgenddate = maximumDateGeneral;
    		casesGraphEndDate.setValue(maximumDateGeneral);
    	} 
    }
    
    /**
     * Searching the countries name
     * @param event This event occurs when the user types in the Case Graph Section's search box 
     */
    
    @FXML
    void CGSearchBox(ActionEvent event) {
    	searchcountry = casesGraphSearch.getText();
    	
    	if (searchcountry == null || searchcountry == "") {
    		ArrayList<String> merged = new ArrayList<>();
        	merged.addAll(casesGraphSelectedList);
        	merged.addAll(casesGraphUnSelectedList);
        	casesGraphList.setItems(FXCollections.observableArrayList(merged));
    	} else {
    		ArrayList<String> newSelectedList = new ArrayList<>();
    		ArrayList<String> newNotSelectedList = new ArrayList<>();
    		searchcountry = searchcountry.toLowerCase();
    		for (String s : casesGraphSelectedList) {
    			if (s.toLowerCase().contains(searchcountry))
    				newSelectedList.add(s);
    		}
    		for (String s : casesGraphUnSelectedList) {
    			if (s.toLowerCase().contains(searchcountry))
    				newNotSelectedList.add(s);
    		}
    		newNotSelectedList.sort(Comparator.naturalOrder());
    		newSelectedList.sort(Comparator.naturalOrder());
    		ArrayList<String> merged = new ArrayList<>();
        	merged.addAll(newSelectedList);
        	merged.addAll(newNotSelectedList);
        	casesGraphList.getItems().clear();
        	casesGraphList.setItems(FXCollections.observableArrayList(merged));
        	
    }
    }
    
    /**
     * Clear the search box in the Case Graph Section
     * @param event This event occurs when the user clicks "X" button in the Case Graph Section
     */
    
    @FXML
    void CGSearchClear(ActionEvent event) {
    	casesGraphSearch.clear();
    	ArrayList<String> merged = new ArrayList<>();
    	merged.addAll(casesGraphSelectedList);
    	merged.addAll(casesGraphUnSelectedList);
    	//System.out.println("-notsearched-");
    	casesGraphList.setItems(FXCollections.observableArrayList(merged));
    }
    
    /**
     * Clear the selection of countries 
     * @param event The event occurs when the user click the "Clear" button under the countries list in Cases Graph Section
     */
    
    @FXML
    void CGSelectClear(ActionEvent event) {
    	ArrayList<String> items = (ArrayList) casesGraphSelectedList.clone();
		for (String item : items) {
			casesGraphUnSelectedList.add(item);
			casesGraphSelectedList.remove(item);
		}
	
	casesGraphUnSelectedList.sort(Comparator.naturalOrder());
	casesGraphList.getItems().clear();
	casesGraphList.setItems(FXCollections.observableArrayList(casesGraphUnSelectedList));
	//CasesTableInfo();
    }
    
    /**
     * Updating the countries that has been selected in the Cases Graph ListView 
     * @param event This event occurs when the user click the countries they want on the Cases Graph List Section
     */
    
    @FXML
    void CGListSelect(MouseEvent event) {
    	String currentList = casesGraphList.getSelectionModel().getSelectedItem().toString();
		
		if(casesGraphSelectedList != null && casesGraphSelectedList.contains(currentList)) {
			casesGraphUnSelectedList.add(currentList);
			casesGraphSelectedList.remove(currentList);
			casesGraphUnSelectedList.sort(Comparator.naturalOrder());
		}
		else if(casesGraphUnSelectedList != null && casesGraphUnSelectedList.contains(currentList)) {
			casesGraphSelectedList.add(currentList);
			casesGraphUnSelectedList.remove(currentList);
			casesGraphSelectedList.sort(Comparator.naturalOrder());
		}
		ArrayList<String> sort = new ArrayList<>();
		sort.addAll(casesGraphSelectedList);
		sort.addAll(casesGraphUnSelectedList);
    	
		casesGraphList.getItems().clear();
		casesGraphList.setItems(FXCollections.observableArrayList(sort));
    }
    
    /**
     * Generate the Graph in the Case Graph Section
     * @param event This event occurs when the user clicks "Generate" button in the Cases Graph Section
     */
    
    @FXML
    void GenerateCasesGraph(ActionEvent event) {
    	CasesLineGraphInfo();
    	CasesGeoGraphInfo();
    }
    
    /**
     * Generate Line Graph (Reference: Charles's code)
     * @author renacheng
     */
    
    public void CasesLineGraphInfo() {
    	casesLineGraph.getData().clear();
    	if (casesGraphSelectedList.size() > 0) {
    		LocalDate startDate = casesGraphStartDate.getValue();
    		LocalDate endDate = casesGraphEndDate.getValue();
    		long daysBetween = startDate.until(endDate, ChronoUnit.DAYS);
    		casesLineGraphX.setAutoRanging(false);
    		casesLineGraphX.setTickUnit(daysBetween / 20);
    		casesLineGraphX.setLowerBound(0);
    		casesLineGraphX.setUpperBound(daysBetween);
    		casesLineGraphX.setTickLabelFormatter(new StringConverter<Number>() {
	      	    @Override
	      	    public String toString(Number object) {
	      	      long daysFromStartDay = object.longValue();
	      	      LocalDate currentDate = startDate.plusDays(daysFromStartDay);
	      	      return String.format("%d/%d/%d",currentDate.getMonthValue(),currentDate.getDayOfMonth(),currentDate.getYear());
	      	    }
	      	    @Override
	      	    public Number fromString(String string) {
	      	      return null;
	      	    }
      	  	});
    		HashMap<String, ConfirmedCase> confirmedCases = DataAnalysis.data.getConfirmedCases();
    		for (String country : casesGraphSelectedList) {
    			XYChart.Series<Number, Number> series = new XYChart.Series();
    			series.setName(country);
    			HashMap<Date, Double> totalCasePerMillion = confirmedCases.get(country).getTotalCasePerMillion();
    			for (LocalDate currentDate = startDate; currentDate.isBefore(endDate) || currentDate.isEqual(endDate); currentDate = currentDate.plusDays(1)) {
    				Date date = Date.from(currentDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    				Double currentValue = totalCasePerMillion.get(date);
    				if (currentValue == null || currentValue < 0)
    					continue;
    				series.getData().add(new XYChart.Data<Number, Number>(startDate.until(currentDate, ChronoUnit.DAYS), currentValue));
    			}
    			casesLineGraph.getData().add(series);
        		casesLineGraph.setCreateSymbols(false);

    			
    		}

    	}
    }
    
    /**
     * Generate Geo Graph(Reference: Justin's code)
     * @author renacheng
     */
    private void CasesGeoGraphInfo() {
    	//set all to gray in case some is black
    	ObservableList<Node> childrensClear = casesGeoGraph.getChildren();
    	for(int i = 0; i<childrensClear.size(); i++) {
    		Node currentNode = childrensClear.get(i);
    		if(currentNode instanceof SVGPath) {
    			((SVGPath)currentNode).setFill(Paint.valueOf("gray"));
    		}
    		else if(currentNode instanceof Group) {
    			ObservableList<Node> childrenOfChildrens = ((Group)currentNode).getChildren();
    			for(int j = 0; j<childrenOfChildrens.size(); j++) {
    				((SVGPath)childrenOfChildrens.get(j)).setFill(Paint.valueOf("gray"));
    			}
    		}
    	}
    	
    	ArrayList<String> locationName = DataAnalysis.data.getLocationName();
    	HashMap<String, ConfirmedCase> cc = MyApplication.data.getConfirmedCases();
     	LocalDate endDate = casesGraphEndDate.getValue();
    	Date currentDateInDate = Date.from(endDate.atStartOfDay(ZoneId.systemDefault()).toInstant()); 
    	
    	
    	for(String loca : locationName) {
    		HashMap<Date, Double> casesperm = cc.get(loca).getTotalCasePerMillion();
    		String currentLocationCode = MyApplication.data.getLocationCode(loca);
    		Double currentValue = casesperm.get(currentDateInDate);
		    if(currentLocationCode == null)
		    	continue;
		    ObservableList<Node> childrens = casesGeoGraph.getChildren();
		    for(Node currentNode : childrens) {
	    		String currentNodeId = currentNode.getId();
	    		if(currentNodeId == null)
	    			continue;
	    		if(currentNodeId.equals(currentLocationCode) ) {
	    			String color = "gray";
	    			if (currentValue == null) {
	    				color = "gray";
	    			} else if(currentValue >=0.0 && currentValue < 20000) {
	    				color = "#D7BDE2";
	    			}
	    			else if(currentValue < 40000) {
	    				color = "#c39bd3";
	    			}
	    			else if(currentValue < 60000) {
	    				color = "#af7ac5";
	    			}
	    			else if(currentValue < 80000) {
	    				color = "#9b59b6";
	    			}
	    			else if(currentValue < 100000) {
	    				color = "#884ea0";
	    			}
	    			else if(currentValue >= 100000) {
	    				color = "#76448a";
	    			}
	    			
    	    		if(currentNode instanceof SVGPath) {
    	    			((SVGPath)currentNode).setFill(Paint.valueOf(color));
    	    		}
    	    		else if(currentNode instanceof Group) {
    	    			ObservableList<Node> childrenOfChildrens = ((Group)currentNode).getChildren();
    	    			for(int n = 0; n<childrenOfChildrens.size(); n++) {
    	    				((SVGPath)childrenOfChildrens.get(n)).setFill(Paint.valueOf(color));
    	    			}
    	    		}
    	    		
	    		}
	    	}
    	}
    }
    	
    /** Task B **/
    
    // Task B1
    private ArrayList<String> deathsTableSelectedList;
    private ArrayList<String> deathsTableNotSelectedList;

    @FXML
    private ListView<String> deathsTableList;
    
    /**
     * Update the ListView on "Deaths Table" section
     * Selecte/Deselecte the choosed Item
     * @param event The event that trigger the function, which is click of a item in the "Deaths Table" ListView
     * @author Charles Cheng
     */
    
    @FXML
    public void deathsTableListUpdate(MouseEvent event) {
    	String selectedItem = deathsTableList.getSelectionModel().getSelectedItem().toString();
    	if (deathsTableNotSelectedList.contains(selectedItem)) {
    		// If the selectedItem is in NotSelectedList -> Change to the SelectedList
    		deathsTableNotSelectedList.remove(new String(selectedItem));
        	deathsTableSelectedList.add(new String(selectedItem));
        	deathsTableSelectedList.sort(Comparator.naturalOrder());
    	} else { 
    		// If the selectedItem is in SelectedList -> Change to the NotSelectedList
    		deathsTableSelectedList.remove(new String(selectedItem));
        	deathsTableNotSelectedList.add(new String(selectedItem));
        	deathsTableNotSelectedList.sort(Comparator.naturalOrder());
    	}
    	/*for (String s : deathsTableSelectedList) {
    		System.out.println("1 " + s + "  ");
    	}
    	System.out.println("\n\n");*/
    	String searchWord = deathsTableSearch.getText();
    	updateDeathsTableListView(searchWord);
    }

    @FXML
    private Button deathsTableClear;
    
    /**
     * Clear the selected countries on "Deaths Table" section
     * @param event The event that trigger the function, which is click of button "Clear Selected" in "Deaths Table" section
     * @author Charles Cheng
     */
    
    @FXML
    public void deathsTableClearSelected(ActionEvent event) {
    	deathsTableSearch.clear();
    	if (deathsTableSelectedList.size() > 0) {
    		ArrayList<String> remove = (ArrayList) deathsTableSelectedList.clone();
    		for (String s : remove) {
    			deathsTableSelectedList.remove(s);
    			deathsTableNotSelectedList.add(s);
    		}
    	}
    	deathsTableNotSelectedList.sort(Comparator.naturalOrder());
    	deathsTableList.setItems(FXCollections.observableArrayList(deathsTableNotSelectedList));
    	updateDeathsTable();
    } 

    @FXML
    private DatePicker deathsTableDate;
    
    /**
     * Update the Deaths Table Title
     * @param event The event that trigger the function, which is change of date value
     * @author Charles Cheng
     */
    
    @FXML
    void deathsTableDateUpdate(ActionEvent event) {
    	LocalDate date = deathsTableDate.getValue();
    	if (date.isBefore(minimumDate)) {
    		deathsTableDate.setValue(minimumDate);
    	} else if (date.isAfter(maximumDateGeneral)) {
    		deathsTableDate.setValue(maximumDateGeneral);
    	}
    }

    @FXML
    private Button deathsTableGenerate;
    
    @FXML
    private TableView<DeathData> deathsTable;
    
    @FXML
    private Text deathsTableTitle;

    @FXML
    private TableColumn<DeathData, String> deathsTableCountry;
    
    @FXML
    private TableColumn<DeathData, String> deathsTableTotal;

    @FXML
    private TableColumn<DeathData, String> deathsTablePer1M;
    
    /**
     * Generate the Deaths Table
     * @param event The event that trigger the function, which is click of button "Gerenate Table" in "Deaths Table" section
     * @author Charles Cheng
     */
    
    @FXML
    public void deathsTableGenerateTable(MouseEvent event) {
    	updateDeathsTable();
    }

    @FXML
    private TextField deathsTableSearch;
    
    @FXML
	private Button deathsTableSearchClear;
    
    /**
     * Update the "Deaths Table" ListView based on the Search value
     * @param event The event that trigger the function, which is chagne of text value in the search text Field of "Deaths Table" section
     * @author Charles Cheng
     */
    
    @FXML
    public void deathsTableSearchCountry(ActionEvent event) {
    	String searchWord = deathsTableSearch.getText();
    	updateDeathsTableListView(searchWord);
    }
    
    /**
     * Remove the Serach text Field value and upadte the "Deaths Table" ListView
     * @param event The event that trigger the function, which is click of button "X" in "Deaths Table" section
     * @author Charles Cheng
     */
    
    @FXML
    public void deathsTableClearSearch(MouseEvent event) {
    	deathsTableSearch.clear();
    	updateDeathsTableListView("");
    }
    
    /**
     * Updating what countries will be shown in the Deaths Table ListView 
     * depends on the searchWord (non case sensitive)
     * @param searchWord
     * @author Charles Cheng
     */
    
    public void updateDeathsTableListView(String searchWord) {
    	if (searchWord == null || searchWord == "") {
    		ArrayList<String> merged = new ArrayList<>();
        	merged.addAll(deathsTableSelectedList);
        	merged.addAll(deathsTableNotSelectedList);
        	//System.out.println("-notsearched-");
        	deathsTableList.setItems(FXCollections.observableArrayList(merged));
    	} else {
    		ArrayList<String> newSelectedList = new ArrayList<>();
    		ArrayList<String> newNotSelectedList = new ArrayList<>();
    		searchWord = searchWord.toLowerCase();
    		for (String s : deathsTableSelectedList) {
    			if (s.toLowerCase().contains(searchWord))
    				newSelectedList.add(s);
    		}
    		for (String s : deathsTableNotSelectedList) {
    			if (s.toLowerCase().contains(searchWord))
    				newNotSelectedList.add(s);
    		}
    		newNotSelectedList.sort(Comparator.naturalOrder());
    		newSelectedList.sort(Comparator.naturalOrder());
    		ArrayList<String> merged = new ArrayList<>();
        	merged.addAll(newSelectedList);
        	merged.addAll(newNotSelectedList);
        	//System.out.println("-searched-");
        	deathsTableList.getItems().clear();
        	deathsTableList.setItems(FXCollections.observableArrayList(merged));
        	//for (String s : deathsTableList.getItems())
        	//	System.out.println(s);
    	}
    }
    
    /**
     * Update the Death Table when the user click the "Generate Table" button
     * @author Charles Cheng
     */
    
    public void updateDeathsTable() {
    	updateDeathsTableTitle();
    	ArrayList arrayList = new ArrayList<>();
    	ObservableList<DeathData> list = FXCollections.observableArrayList(arrayList);
    	HashMap<String, ConfirmedDeath> confirmedDeaths = MyApplication.data.getConfirmedDeaths();
    	LocalDate localDate = deathsTableDate.getValue();
    	Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant()); 
    	if (date != null) {
	    	for (String s : deathsTableSelectedList) {
	    		//System.out.println(s);
	    		Long total = confirmedDeaths.get(s).getTotalCaseDeathDateSpecific(date);
	    		Double perMillion = confirmedDeaths.get(s).getTotalDeathPerMillionDateSpecific(date);
	    		//System.out.println(s + " " + total + " " + perMillion);
	    		DeathData newData = new DeathData(s, total, perMillion);
	    		list.add(newData);
	    	}
	    	deathsTableCountry.setCellValueFactory(new PropertyValueFactory<DeathData, String>("country"));
	    	deathsTableTotal.setCellValueFactory(new PropertyValueFactory<DeathData, String>("total"));
	    	deathsTablePer1M.setCellValueFactory(new PropertyValueFactory<DeathData, String>("perMillion"));
	    	deathsTable.setItems(list);
    	}
    }
    
    /**
     * Updating the Deaths Table Title when the selected date is changed
     * @author Charles Cheng
     */
    public void updateDeathsTableTitle() {
    	LocalDate localDate = deathsTableDate.getValue();
    	if (localDate != null)
    		deathsTableTitle.setText("Number of Confirmed COVID-19 Deaths as of " + localDate.toString());
    	else
    		deathsTableTitle.setText("Number of Confirmed COVID-19 Deaths");
    }
    
    // Task B2
    
    private ArrayList<String> deathsGraphSelectedList;
    private ArrayList<String> deathsGraphNotSelectedList;
    
    @FXML
    private Group deathsGeoGraph;

    @FXML
    private Button deathsGraphClear;

    @FXML
    private AnchorPane deathsGraphGeoPane;

    @FXML
    private BorderPane deathsGraphLinePane;

    @FXML
    private ListView<String> deathsGraphList;
    
    /**
     * Update the ListView on "Deaths Graph" section
     * Selecte/Deselecte the choosed Item
     * @param event The event that trigger the function, which is click of a item in the "Deaths Graph" ListView
     * @author Charles Cheng
     */
    
    @FXML
    public void deathsGraphListUpdate(MouseEvent event) {
    	String selectedItem = deathsGraphList.getSelectionModel().getSelectedItem().toString();
    	if (deathsGraphNotSelectedList.contains(selectedItem)) {
    		// If the selectedItem is in NotSelectedList -> Change to the SelectedList
    		deathsGraphNotSelectedList.remove(new String(selectedItem));
        	deathsGraphSelectedList.add(new String(selectedItem));
        	deathsGraphSelectedList.sort(Comparator.naturalOrder());
    	} else { 
    		// If the selectedItem is in SelectedList -> Change to the NotSelectedList
    		deathsGraphSelectedList.remove(new String(selectedItem));
        	deathsGraphNotSelectedList.add(new String(selectedItem));
        	deathsGraphNotSelectedList.sort(Comparator.naturalOrder());
    	}
    	/*for (String s : deathsGraphSelectedList) {
    		System.out.println("1 " + s + "  ");
    	}
    	System.out.println("\n\n");*/
    	String searchWord = deathsGraphSearch.getText();
    	updateDeathsGraphListView(searchWord);
    }
     
    @FXML
    private TextField deathsGraphSearch;
    
    @FXML
	private Button deathsGraphSearchClear;
    
    /**
     * Clear the selected countries on "Deaths Graph" section
     * @param event The event that trigger the function, which is click of button "clear selected" in "Deaths Graph" section
     * @author Charles Cheng
     */
    
    @FXML
    public void deathsGraphClearSelected(MouseEvent event) {
    	deathsGraphSearch.clear();
    	if (deathsGraphSelectedList.size() > 0) {
    		ArrayList<String> remove = (ArrayList) deathsGraphSelectedList.clone();
    		for (String s : remove) {
    			deathsGraphSelectedList.remove(s);
    			deathsGraphNotSelectedList.add(s);
    		}
    	}
    	deathsGraphNotSelectedList.sort(Comparator.naturalOrder());
    	deathsGraphList.setItems(FXCollections.observableArrayList(deathsGraphNotSelectedList));
    	updateDeathsLineGraph();
    } 
    
    @FXML
    private Button deathsGraphGenerate;
    
    /**
     * Generate the Deaths Graphs by calling updateDeathsGraph()
     * @param event The event that trigger the function, which is click of button "Gerenate Table" in "Deaths Graph" section
     * @author Charles Cheng
     */
    
    @FXML
    public void deathsGraphGenerateGraphs(MouseEvent event) {
    	updateDeathsGraph();
    }
    
    @FXML
    private DatePicker deathsGraphStartDate;
    
    @FXML
    private DatePicker deathsGraphEndDate;
    
    @FXML
    void deathsGraphStartDateUpdate(ActionEvent event) {
    	LocalDate date = deathsGraphStartDate.getValue();
    	if (date.isBefore(minimumDate)) {
    		deathsGraphStartDate.setValue(minimumDate);
    	} else if (date.isAfter(LocalDate.of(2021,7,19))) {
    		deathsGraphStartDate.setValue(LocalDate.of(2021,7,19));
    		deathsGraphEndDate.setValue(LocalDate.of(2021,7,20));
    	}
    	if (!date.isBefore(deathsGraphEndDate.getValue())) {
    		deathsGraphEndDate.setValue(date.plusDays(1));
    	}
    }
    
    @FXML
    private Text deathsGeoGraphTitle;
    
    @FXML
    private Text deathsLineGraphTitle;
    
    @FXML
    private LineChart<Number, Number> deathsLineGraph;

    @FXML
    private NumberAxis deathsLineGraphX;

    @FXML
    private NumberAxis deathsLineGraphY;
    
    /**
     * Update the "Deaths Graph" ListView based on the Search value
     * @param event The event that trigger the function, which is chagne of text value in the search text Field of "Deaths Graph" section
     * @author Charles Cheng
     */
    
    @FXML
    public void deathsGraphSearchCountry(ActionEvent event) {
    	String searchWord = deathsGraphSearch.getText();
    	updateDeathsGraphListView(searchWord);
    }
    
    /**
     * Remove the Serach text Field value and upadte the "Deaths Graph" ListView
     * @param event The event that trigger the function, which is click of button "X" in "Deaths Graph" section
     * @author Charles Cheng
     */
    
    @FXML
    public void deathsGraphClearSearch(MouseEvent event) {
    	deathsGraphSearch.clear();
    	updateDeathsGraphListView("");
    }
    
    /**
     * Updating what countries will be shown in the Deaths Graph ListView 
     * depends on the searchWord (non case sensitive)
     * @param searchWord
     * @author Charles Cheng
     */
    public void updateDeathsGraphListView(String searchWord) {
    	if (searchWord == null || searchWord == "") {
    		ArrayList<String> merged = new ArrayList<>();
        	merged.addAll(deathsGraphSelectedList);
        	merged.addAll(deathsGraphNotSelectedList);
        	//System.out.println("-notsearched-");
        	deathsGraphList.setItems(FXCollections.observableArrayList(merged));
    	} else {
    		ArrayList<String> newSelectedList = new ArrayList<>();
    		ArrayList<String> newNotSelectedList = new ArrayList<>();
    		searchWord = searchWord.toLowerCase();
    		for (String s : deathsGraphSelectedList) {
    			if (s.toLowerCase().contains(searchWord))
    				newSelectedList.add(s);
    		}
    		for (String s : deathsGraphNotSelectedList) {
    			if (s.toLowerCase().contains(searchWord))
    				newNotSelectedList.add(s);
    		}
    		newNotSelectedList.sort(Comparator.naturalOrder());
    		newSelectedList.sort(Comparator.naturalOrder());
    		ArrayList<String> merged = new ArrayList<>();
        	merged.addAll(newSelectedList);
        	merged.addAll(newNotSelectedList);
        	//System.out.println("-searched-");
        	deathsGraphList.getItems().clear();
        	deathsGraphList.setItems(FXCollections.observableArrayList(merged));
        	//for (String s : deathsGraphList.getItems())
        	//	System.out.println(s);
    	}
    }
    
    /**
     * Generate the Deaths Line Graph and Geo Graph
     * @author Charles Cheng
     */

    public void updateDeathsGraph() {
    	updateDeathsLineGraph();
    	updateDeathsGeoGraph();
    }
    
    public void updateDeathsLineGraph() {
    	deathsLineGraphTitle.setText("Cumulative Confirmed COVID-19 Deaths (per 1M) from " + deathsGraphStartDate.getValue().toString() + " to " + deathsGraphEndDate.getValue().toString());
    	deathsLineGraph.getData().clear();
    	if (deathsGraphSelectedList.size() > 0) {
    		LocalDate startDate = deathsGraphStartDate.getValue();
    		LocalDate endDate = deathsGraphEndDate.getValue();
    		long daysBetween = startDate.until(endDate, ChronoUnit.DAYS);
    		deathsLineGraphX.setAutoRanging(false);
    		deathsLineGraphX.setTickUnit(daysBetween / 20);
    		deathsLineGraphX.setLowerBound(0);
    		deathsLineGraphX.setUpperBound(daysBetween);
    		deathsLineGraphX.setTickLabelFormatter(new StringConverter<Number>() {
	      	    @Override
	      	    public String toString(Number object) {
	      	      long daysFromStartDay = object.longValue();
	      	      LocalDate currentDate = startDate.plusDays(daysFromStartDay);
	      	      return String.format("%d/%d/%d",currentDate.getMonthValue(),currentDate.getDayOfMonth(),currentDate.getYear());
	      	    }
	      	    @Override
	      	    public Number fromString(String string) {
	      	      return null;
	      	    }
      	  	});
    		deathsLineGraph.setCreateSymbols(false);
    		HashMap<String, ConfirmedDeath> confirmedDeaths = MyApplication.data.getConfirmedDeaths();
    		for (String country : deathsGraphSelectedList) {
    			XYChart.Series<Number, Number> series = new XYChart.Series();
    			series.setName(country);
    			HashMap<Date, Double> totalDeathPerMillion = confirmedDeaths.get(country).getTotalDeathPerMillion();
    			for (LocalDate currentDate = startDate; currentDate.isBefore(endDate) || currentDate.isEqual(endDate); currentDate = currentDate.plusDays(1)) {
    				Date date = Date.from(currentDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    				Double currentValue = totalDeathPerMillion.get(date);
    				if (currentValue == null || currentValue < 0)
    					continue;
    				series.getData().add(new XYChart.Data<Number, Number>(startDate.until(currentDate, ChronoUnit.DAYS), currentValue));
    			}
    			deathsLineGraph.getData().add(series);
    		}
    	}
    }
    
    /**
     * Create Deaths GeoGraph (Reference: Justin's code)
     * @author Charles Cheng
     */
    
    public void updateDeathsGeoGraph() {
    	deathsGeoGraphTitle.setText("Cumulative Confirmed COVID-19 Deaths (per 1M) on " + deathsGraphEndDate.getValue().toString());
    	
    	//set all to gray in case some is black
    	ObservableList<Node> childrensClear = deathsGeoGraph.getChildren();
    	for(int i = 0; i<childrensClear.size(); i++) {
    		Node currentNode = childrensClear.get(i);
    		if(currentNode instanceof SVGPath) {
    			((SVGPath)currentNode).setFill(Paint.valueOf("gray"));
    		}
    		else if(currentNode instanceof Group) {
    			ObservableList<Node> childrenOfChildrens = ((Group)currentNode).getChildren();
    			for(int j = 0; j<childrenOfChildrens.size(); j++) {
    				((SVGPath)childrenOfChildrens.get(j)).setFill(Paint.valueOf("gray"));
    			}
    		}
    	}
    	
    	ArrayList<String> locationName = (ArrayList) MyApplication.data.getLocationName().clone();
    	HashMap<String, ConfirmedDeath> confirmedDeaths = MyApplication.data.getConfirmedDeaths();
    	LocalDate endDate = deathsGraphEndDate.getValue();
    	Date currentDateInDate = Date.from(endDate.atStartOfDay(ZoneId.systemDefault()).toInstant()); 
    	
    	for(String currentLocation : locationName) {
    		HashMap<Date, Double> locationDeathsPerMillion = confirmedDeaths.get(currentLocation).getTotalDeathPerMillion();
    		String currentLocationCode = MyApplication.data.getLocationCode(currentLocation);
    		Double currentValue = locationDeathsPerMillion.get(currentDateInDate);
		    if(currentLocationCode == null)
		    	continue;
		    ObservableList<Node> childrens = deathsGeoGraph.getChildren();
	    	for(Node currentNode : childrens) {
	    		String currentNodeId = currentNode.getId();
	    		if(currentNodeId == null)
	    			continue;
	    		if(currentNodeId.equals(currentLocationCode) ) {
	    			String color = "gray";
	    			if (currentValue == null) {
	    				color = "gray";
	    			} else if(currentValue >=0.0 && currentValue < 500) {
	    				color = "#fffed1";
	    			}
	    			else if(currentValue < 1000) {
	    				color = "#fceda9";
	    			}
	    			else if(currentValue < 1500) {
	    				color = "#f9d984";
	    			}
	    			else if(currentValue < 2000) {
	    				color = "#f4b45f";
	    			}
	    			else if(currentValue < 2500) {
	    				color = "#ef924f";
	    			}
	    			else if(currentValue < 3000) {
	    				color = "#ea5b3b";
	    			}
	    			else if(currentValue < 3500) {
	    				color = "#d1352b";
	    			}
	    			else if (currentValue >= 3500) {
	    				color = "#a2202c";
	    			}
	    			
    	    		if(currentNode instanceof SVGPath) {
    	    			((SVGPath)currentNode).setFill(Paint.valueOf(color));
    	    			((SVGPath)currentNode).setStroke(Paint.valueOf("#000000"));
    	    		}
    	    		else if(currentNode instanceof Group) {
    	    			ObservableList<Node> childrenOfChildrens = ((Group)currentNode).getChildren();
    	    			for(int n = 0; n<childrenOfChildrens.size(); n++) {
    	    				((SVGPath)childrenOfChildrens.get(n)).setFill(Paint.valueOf(color));
    	    				((SVGPath)childrenOfChildrens.get(n)).setStroke(Paint.valueOf("#000000"));
    	    			}
    	    		}
    	    		
	    		}
	    	}
    	}
    }
    
    /** Task C **/

    @FXML
    private Group vaccinationGeoGraph;

    @FXML
    private Button vaccinationGraphClear;
    
    @FXML
	private Button vaccinationGraphSearchClear;

    @FXML
    private DatePicker vaccinationGraphEndDate;

    @FXML
    private Button vaccinationGraphGenerate;

    @FXML
    private AnchorPane vaccinationGraphGeoPane;

    @FXML
    private BorderPane vaccinationGraphLinePane;

    @FXML
    private ListView<String> vaccinationGraphList;

    @FXML
    private TextField vaccinationGraphSearch;

    @FXML
    private DatePicker vaccinationGraphStartDate;

    @FXML
    private LineChart<Number, Number> vaccinationLineGraph;

    @FXML
    private NumberAxis vaccinationLineGraphX;

    @FXML
    private NumberAxis vaccinationLineGraphY;
    
    @FXML
    private Text vaccinationGraphTitle;
    
    @FXML
    private Text vaccinationGeoGraphTitle;
    

    @FXML
    private TableView vaccinationTable;

    @FXML
    private Button vaccinationTableClear;
    
    @FXML
	private Button vaccinationTableSearchClear;

    @FXML
    private TableColumn<?, ?> vaccinationTableCountry;

    @FXML
    private DatePicker vaccinationTableDate;

    @FXML
    private Button vaccinationTableGenerate;

    @FXML
    private ListView<String> vaccinationTableList;

    @FXML
    private TableColumn<?, ?> vaccinationTablePer1M;

    @FXML
    private TextField vaccinationTableSearch;

    @FXML
    private Text vaccinationTableTitle;

    @FXML
    private TableColumn<?, ?> vaccinationTableTotal;
   
    @FXML
    private Tab vaccinationTableTab;
    
    /**
     * Get the Ending Date for the period of line chart
     * @param event The event that trigger the function, which is change of date value
     */

    @FXML
    public void getEndDate(ActionEvent event) {
    	LocalDate endDate = vaccinationGraphEndDate.getValue();
    	if(!endDate.isAfter(rovStartDate)) {
    		endDate = rovStartDate.plusDays(1);
    		vaccinationGraphEndDate.setValue(endDate);
    	}
    	else if(endDate.isAfter(maximumDateGeneral)) {
    		endDate = maximumDateGeneral;
    		vaccinationGraphEndDate.setValue(maximumDateGeneral);
    	}
    	rovEndDate = endDate;
//    	LocalDate ld = vaccinationGraphEndDate.getValue();
//    	String strDate = String.format("%d/%d/%d",ld.getMonthValue(),ld.getDayOfMonth(),ld.getYear());
//    	rovEndDate = new Date(strDate);
    }
    
    /**
     * Get the Start Date for the period of line chart
     * @param event The event that trigger the function, which is change of date value
     */

    @FXML
    public void getStartDate(ActionEvent event) {
    	LocalDate startDate = vaccinationGraphStartDate.getValue();
    	if(startDate.isBefore(minimumDate)) {
    		startDate = minimumDate;
    		vaccinationGraphStartDate.setValue(minimumDate);
    	}
    	else if(startDate.isAfter(maximumDateGraph)) {
    		startDate = maximumDateGraph;
    		vaccinationGraphStartDate.setValue(maximumDateGraph);
    	}
    	rovStartDate = startDate;
    	if(!startDate.isBefore(rovEndDate)) {
    		getEndDate(event);
    	}
    		
    		
    	
//    	LocalDate ld = vaccinationGraphStartDate.getValue();
//    	String strDate = String.format("%d/%d/%d",ld.getMonthValue(),ld.getDayOfMonth(),ld.getYear());
//    	rovStartDate = new Date(strDate);
    }
    
    
    /**
     * Search through the selected and unselected places and update the list to show the countries containing the text
     * @param event The event that trigger the function, which is change of text value in the search text Field of vaccination graph section
     */
    @FXML
    public void searchGraphCountry(ActionEvent event) {
    	currentGraphSearch = vaccinationGraphSearch.getText();
    	updateGraphListView(0);
    }
    
    /**
     * Clean the selected places and update the list view for vaccination graph
     * @param event The event that trigger the function, which is click of button "clear selected" in vaccination graph section
     */
    @FXML
    public void clearGraphSelect(ActionEvent event) {
    	unselectedGraphCountries = (ArrayList) MyApplication.data.getLocationName().clone();
		unselectedGraphCountries.sort(Comparator.naturalOrder());
    	selectedGraphCountries = new ArrayList<String>();
    	updateGraphListView(2);
    	clearSelect = true;
    	updateGraphView();
    	clearSelect = false;
    	
    }
    
    /**
     * Generate the cummutative graph and the geograph of vaccination rate
     * @param event The event that trigger the function, which is click of button "Generate Graph" in vaccination graph section
     */
    @FXML
    public void generateRateGraph(ActionEvent event) {
    	//System.out.println("pressed");
    	updateGraphView();
    	
    }
    
    /**
     * Clean the search text field in vaccination graph section
     * @param event The event that trigger the function, which is click of button "X" in vaccination graph section
     */
    @FXML
    public void graphClearSearchText(ActionEvent event) {
    	vaccinationGraphSearch.clear();
    	currentGraphSearch = "";
    	updateGraphListView(0);
    	
    }
    
    /**
     * Clean the search text field in vaccination table section
     * @param event The event that trigger the function, which is click of button "X" in vaccination table section
     */
    @FXML
    public void tableClearSearchText(ActionEvent event) {
    	vaccinationTableSearch.clear();
    	currentSearch = "";
    	updateListView(0);
    	
    }
    
    /**
     * update the List view in the vaccination graph section
     * @param index 0:call when searching 1:call when adding selecting countries 2:call when initializing
     */
    public void updateGraphListView(int index) {
    	//System.out.println("updateGraphListView");
    	vaccinationGraphList.getItems().clear();
    	ArrayList<String> list = new ArrayList<String>();
    	if(index == 0) {
    		
    		if (currentGraphSearch == null || currentGraphSearch == "") {
        		ArrayList<String> merged = new ArrayList<>();
            	merged.addAll(selectedGraphCountries);
            	merged.addAll(unselectedGraphCountries);
            	//System.out.println("-notsearched-");
            	vaccinationGraphList.setItems(FXCollections.observableArrayList(merged));
        	} else {
        		ArrayList<String> newSelectedList = new ArrayList<>();
        		ArrayList<String> newNotSelectedList = new ArrayList<>();
        		currentGraphSearch = currentGraphSearch.toLowerCase();
        		for (String s : selectedGraphCountries) {
        			if (s.toLowerCase().contains(currentGraphSearch))
        				newSelectedList.add(s);
        		}
        		for (String s : unselectedGraphCountries) {
        			if (s.toLowerCase().contains(currentGraphSearch))
        				newNotSelectedList.add(s);
        		}
        		newNotSelectedList.sort(Comparator.naturalOrder());
        		newSelectedList.sort(Comparator.naturalOrder());
        		ArrayList<String> merged = new ArrayList<>();
            	merged.addAll(newSelectedList);
            	merged.addAll(newNotSelectedList);
            	//System.out.println("-searched-");
            	//vaccinationGraphList.getItems().clear();
            	vaccinationGraphList.setItems(FXCollections.observableArrayList(merged));
            	//for (String s : deathsTableList.getItems())
            	//	System.out.println(s);
        	}
    		return;
    		
    	}
    	else if(index == 1) {
    		//rememver to add color
    		list.addAll(selectedGraphCountries);
    		list.addAll(unselectedGraphCountries);
    	}
    	else if(index == 2) {
    		//initialize
    		list.addAll(unselectedGraphCountries);
    		list.sort(Comparator.naturalOrder());
    	}
    	
        
        
        vaccinationGraphList.setItems(FXCollections.observableArrayList(list));

    }
    
    /**
     * update the geograph view in the vaccination graph section
     * @param date The end date in the vaccination graph section 
     */
    public void updateGeoGraphView(LocalDate date) {
    	//set all to gray in case some is black
    	ObservableList<Node> childrensClear = vaccinationGeoGraph.getChildren();
    	for(int i = 0; i<childrensClear.size(); i++) {
    		Node currentNode = childrensClear.get(i);
    		if(currentNode instanceof SVGPath) {
    			((SVGPath)currentNode).setFill(Paint.valueOf("gray"));
    		}
    		else if(currentNode instanceof Group) {
    			ObservableList<Node> childrenOfChildrens = ((Group)currentNode).getChildren();
    			for(int j = 0; j<childrenOfChildrens.size(); j++) {
    				((SVGPath)childrenOfChildrens.get(j)).setFill(Paint.valueOf("gray"));
    			}
    		}
    	}
    	
    	
    	ArrayList<String> locationName = DataAnalysis.data.getLocationName();
    	
    	
    	for(int i = 0; i<locationName.size();i++) {
    		HashMap<Date, Double> countryRov= DataAnalysis.graphRateOfVaccination.get(locationName.get(i)).getRateOfVaccination();
    		String currentLocationCode = DataAnalysis.data.getLocationCode(locationName.get(i));
    		
    		String strDate = String.format("%d/%d/%d",date.getMonthValue(),date.getDayOfMonth(),date.getYear());
			//System.out.println(strDate);
    		Date currentDateInDate = new Date(strDate);
    		Double endDateValue = countryRov.get(currentDateInDate);
    		
		    //System.out.println("currentLocationCode = "+currentLocationCode);
		    if(currentLocationCode == null)
		    	continue;
		    ObservableList<Node> childrens = vaccinationGeoGraph.getChildren();
	    	for(int m = 0; m<childrens.size(); m++) {
	    		Node currentNode = childrens.get(m);
	    		//System.out.println(currentNode);
	    		String currentNodeId = currentNode.getId();
	    		if(currentNodeId == null)
	    			continue;
	    		if(currentNodeId.equals(currentLocationCode) ) {
	    			//System.out.println("Node equal");
	    			String color = "gray";
	    			if(endDateValue >=0.0 && endDateValue <= 5.0) {
	    				color = "#00ffff";
	    			}
	    			else if(endDateValue >=5.0 && endDateValue <= 10.0) {
	    				color = "#00ccff";
	    			}
	    			else if(endDateValue >=10.0 && endDateValue <= 20.0) {
	    				color = "#0099cc";
	    			}
	    			else if(endDateValue >=20.0 && endDateValue <= 30.0) {
	    				color = "#0099ff";
	    			}
	    			else if(endDateValue >=30.0 && endDateValue <= 40.0) {
	    				color = "#0066cc";
	    			}
	    			else if(endDateValue >=40.0 && endDateValue <= 50.0) {
	    				color = "#0066ff";
	    			}
	    			else if(endDateValue >=50.0 && endDateValue <= 60.0) {
	    				color = "#0033ff";
	    			}
	    			else if(endDateValue >= 60.0 && endDateValue <= 70.0) {
	    				color = "#003399";
	    			}
	    			else if(endDateValue >= 70.0 && endDateValue <= 100.0) {
	    				color = "#000099";
	    			}
	    			
	    			
    	    		if(currentNode instanceof SVGPath) {
    	    			((SVGPath)currentNode).setFill(Paint.valueOf(color));
    	    		}
    	    		else if(currentNode instanceof Group) {
    	    			ObservableList<Node> childrenOfChildrens = ((Group)currentNode).getChildren();
    	    			for(int n = 0; n<childrenOfChildrens.size(); n++) {
    	    				((SVGPath)childrenOfChildrens.get(n)).setFill(Paint.valueOf(color));
    	    			}
    	    		}
	    		}
	    	}
    	}
    }
    
    /**
     * updating the graph view (including both line chart and geograph) in the vaccination graph section
     */
    public void updateGraphView() {
    	//ObservableList<XYChart.Series<Date, Number>> series = FXCollections.observableArrayList();
//    	NumberAxis numberAxis = new NumberAxis();
//    	NumberAxis dateAxis = new NumberAxis();
//    	LineChart<Number, Number> lineChart = new LineChart<>(dateAxis, numberAxis);
    	vaccinationLineGraph.getData().clear();
    	
    	ObservableList<Node> childrensClear = vaccinationGeoGraph.getChildren();
    	for(int i = 0; i<childrensClear.size(); i++) {
    		Node currentNode = childrensClear.get(i);
    		if(currentNode instanceof SVGPath) {
    			((SVGPath)currentNode).setFill(Paint.valueOf("#000000"));
    		}
    		else if(currentNode instanceof Group) {
    			ObservableList<Node> childrenOfChildrens = ((Group)currentNode).getChildren();
    			for(int j = 0; j<childrenOfChildrens.size(); j++) {
    				((SVGPath)childrenOfChildrens.get(j)).setFill(Paint.valueOf("#000000"));
    			}
    		}
    	}
    	
    	if(selectedGraphCountries.size() == 0 || rovStartDate == null || rovEndDate == null
    			|| rovStartDate.isAfter(rovEndDate)) {
    		vaccinationGraphTitle.setText("Cumulative Rate of Vaccination against COVID-19");
    		vaccinationGeoGraphTitle.setText("Rate of Vaccination against COVID-19");
    		//int checkif = 0;
    	}
    	else {
    		//System.out.println("execute1");
//    		for(int i = 0; i<selectedGraphCountries.size(); i++) {
//    			System.out.println(selectedGraphCountries.get(i));
//    		}
    		
    		vaccinationGraphTitle.setText("Cumulative Rate of Vaccination against COVID-19 from "+ 
    					String.format("%d/%d/%d",rovStartDate.getMonthValue(),rovStartDate.getDayOfMonth(),rovStartDate.getYear())
    					+" to " + String.format("%d/%d/%d",rovEndDate.getMonthValue(),rovEndDate.getDayOfMonth(),rovEndDate.getYear()) );
    		vaccinationGeoGraphTitle.setText("Rate of Vaccination against COVID-19 on "+ 
					String.format("%d/%d/%d",rovEndDate.getMonthValue(),rovEndDate.getDayOfMonth(),rovEndDate.getYear()) );
    		
    		setupXAxis(vaccinationLineGraphX, 0, rovStartDate.until(rovEndDate, ChronoUnit.DAYS));
    		//System.out.println("execute2");
//    		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");   
//    		String titleForDate = "Rate of Vaccination against COVID-19 as of " + sdf.format(tableDateOfInterest);
//    		vaccinationTableTitle.setText(titleForDate);
    		vaccinationLineGraph.setCreateSymbols(false);
    		for(int i = 0; i<selectedGraphCountries.size(); i++) {
    		    XYChart.Series<Number, Number> countrySeries = new XYChart.Series<Number, Number>();
        	 
    		    Double endDateValue = -1.0;
    			String currentCountry = selectedGraphCountries.get(i);
    			HashMap<Date, Double> countryRov= DataAnalysis.graphRateOfVaccination.get(currentCountry).getRateOfVaccination();
    			
    			long lastDay = rovStartDate.until(rovEndDate, ChronoUnit.DAYS);
    			for(long j = 0; j<= rovStartDate.until(rovEndDate, ChronoUnit.DAYS);j++){
    				//System.out.println("execute5");
    				LocalDate currentDate = rovStartDate.plus(j,ChronoUnit.DAYS);
    				String strDate = String.format("%d/%d/%d",currentDate.getMonthValue(),currentDate.getDayOfMonth(),currentDate.getYear());
    				//System.out.println(strDate);
            		Date currentDateInDate = new Date(strDate);
            		Double currentValue = countryRov.get(currentDateInDate);
    				//long daysFromStartDay = rovStartDate.until(currentDate, ChronoUnit.DAYS);
            		if(j == lastDay)
            			endDateValue = currentValue;
            		if(currentValue < 0) {
            			//System.out.println("execute6");
            			continue;
            		}
            			
            	    countrySeries.getData().add(new XYChart.Data<Number, Number>(j, currentValue));
            	    
	    		}
    			//System.out.println("execute3");
    		    countrySeries.setName(currentCountry);
    		    //lineChart.getData().add(countrySeries);
    		    vaccinationLineGraph.getData().add(countrySeries);
    		    
    		    /*
    		    String currentLocationCode = DataAnalysis.data.getLocationCode(currentCountry);
    		    System.out.println("currentLocationCode = "+currentLocationCode);
    		    if(currentLocationCode == null)
    		    	continue;
    		    ObservableList<Node> childrens = vaccinationGeoGraph.getChildren();
    	    	for(int m = 0; m<childrens.size(); m++) {
    	    		Node currentNode = childrens.get(m);
    	    		System.out.println(currentNode);
    	    		String currentNodeId = currentNode.getId();
    	    		if(currentNodeId == null)
    	    			continue;
    	    		if(currentNodeId.equals(currentLocationCode) ) {
    	    			System.out.println("Node equal");
    	    			String color = "gray";
    	    			if(endDateValue >=0.0 && endDateValue <= 5.0) {
    	    				color = "#00ffff";
    	    			}
    	    			else if(endDateValue >=5.0 && endDateValue <= 10.0) {
    	    				color = "#00ccff";
    	    			}
    	    			else if(endDateValue >=10.0 && endDateValue <= 20.0) {
    	    				color = "#0099cc";
    	    			}
    	    			else if(endDateValue >=20.0 && endDateValue <= 30.0) {
    	    				color = "#0099ff";
    	    			}
    	    			else if(endDateValue >=30.0 && endDateValue <= 40.0) {
    	    				color = "#0066cc";
    	    			}
    	    			else if(endDateValue >=40.0 && endDateValue <= 50.0) {
    	    				color = "#0066ff";
    	    			}
    	    			else if(endDateValue >=50.0 && endDateValue <= 60.0) {
    	    				color = "#0033ff";
    	    			}
    	    			else if(endDateValue >= 60.0 && endDateValue <= 70.0) {
    	    				color = "#003399";
    	    			}
    	    			else if(endDateValue >= 70.0 && endDateValue <= 100.0) {
    	    				color = "#000099";
    	    			}
    	    			
    	    			
	    	    		if(currentNode instanceof SVGPath) {
	    	    			((SVGPath)currentNode).setFill(Paint.valueOf(color));
	    	    		}
	    	    		else if(currentNode instanceof Group) {
	    	    			ObservableList<Node> childrenOfChildrens = ((Group)currentNode).getChildren();
	    	    			for(int n = 0; n<childrenOfChildrens.size(); n++) {
	    	    				((SVGPath)childrenOfChildrens.get(n)).setFill(Paint.valueOf(color));
	    	    			}
	    	    		}
	    	    		
	    	    		
    	    		}
    	    	
    	    	}
    			*/
    			updateGeoGraphView(rovEndDate);
    		
    			
    		}
    		
    		
    		
    	}
    	
    	if(selectedGraphCountries.size() == 0 && rovStartDate != null && rovEndDate != null && clearSelect != true) {
    		vaccinationGeoGraphTitle.setText("Rate of Vaccination against COVID-19 on "+ 
					String.format("%d/%d/%d",rovEndDate.getMonthValue(),rovEndDate.getDayOfMonth(),rovEndDate.getYear()) );
    			updateGeoGraphView(rovEndDate);
    	}
	    		
//    	if(selectedGraphCountries.size() == 0 || rovStartDate == null || rovEndDate == null
//    			|| rovStartDate.isAfter(rovEndDate)) 
//    		vaccinationLineGraph = lineChart;
    	//System.out.println("execute4");
    }
    
    
    /**
     * change the scales of a number axis into dates.   
     * @param numberAxis the axis that needs to be set up
     * @param min the minimum number value 
     * @param max the maximum number value
     */
    public void setupXAxis(NumberAxis numberAxis, long min, long max) {
    	  numberAxis.setAutoRanging(false);
    	  numberAxis.setTickUnit((max - min) / 20);
    	  numberAxis.setLowerBound(min);
    	  numberAxis.setUpperBound(max);
    	  numberAxis.setTickLabelFormatter(new StringConverter<Number>() {
    	  
          //private final SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy\nHH:mm:ss,SSS");
    	    @Override
    	    public String toString(Number object) {
    	      long daysFromStartDay = object.longValue();
    	      LocalDate currentDate = rovStartDate.plusDays(daysFromStartDay);
    	      return String.format("%d/%d/%d",currentDate.getMonthValue(),currentDate.getDayOfMonth(),currentDate.getYear());
    	    }
    	    @Override
    	    public Number fromString(String string) {
    	      return null;
    	    }
    	 });
    }
    
    
    /**
     * Clean the selected places and update the list view for vaccination table
     * @param event The event that trigger the function, which is click of button "clear selected" in vaccination table section
     */
    @FXML
    public void clearTableSelect(ActionEvent event) {
    	unselectedCountries = (ArrayList) MyApplication.data.getLocationName().clone();
		unselectedCountries.sort(Comparator.naturalOrder());
    	selectedCountries = new ArrayList<String>();
    	updateListView(2);
    	updateTableView();
    	
    }
    
    /**
     * Get the Date of interest for the vaccination table
     * @param event The event that trigger the function, which is change of date value
     */
    @FXML
    public void getDateOfInterest(ActionEvent event) {
    	
    	LocalDate ld = vaccinationTableDate.getValue();
    	if(ld.isBefore(minimumDate)) {
    		ld = minimumDate;
    		vaccinationTableDate.setValue(minimumDate);
    	}
    	else if(ld.isAfter(maximumDateGeneral)) {
    		ld = maximumDateGeneral;
    		vaccinationTableDate.setValue(maximumDateGeneral);
    	}
    	String strDate = String.format("%d/%d/%d",ld.getMonthValue(),ld.getDayOfMonth(),ld.getYear());
    	tableDateOfInterest = new Date(strDate);
    	//Date date = new Date("3/1/2020");
//    	System.out.println(tableDateOfInterest);
//    	System.out.println(date);
    }

    /**
     * Search through the selected and unselected places and update the list to show the countries containing the text
     * @param event The event that trigger the function, which is change of text value in the search text Field of vaccination table section
     */
    @FXML
    public void searchCountrySelection(ActionEvent event) {
    	currentSearch = vaccinationTableSearch.getText();
    	updateListView(0);
    }
    
    
    /**
     * generate the table for vaccination rate and total vaccination according to the date of interest and selected places
     * @param event The event that trigger the function, which is click of button "Generate Table" in vaccination table section
     */
    @FXML
    public void generateRateTable(ActionEvent event) {
    	if(tableDateOfInterest != null)
    		updateTableView();
    }
    
    /** Initialize the variables, date values, and list views for every section
     * 
     * **/
    
    @Override
    public void initialize(URL location, ResourceBundle resources){
    	//Task A1
    	casesTableSelectedList = new ArrayList<>();
    	casesTableUnSelectedList = (ArrayList) MyApplication.data.getLocationName().clone();
    	casesTableUnSelectedList.sort(Comparator.naturalOrder());
    	casesTableList.setItems(FXCollections.observableArrayList(casesTableUnSelectedList));
    	casesTableDate.setValue(LocalDate.of(2020,3,1));
    	casesTableTitle.setText("Number of Confirmed COVID-19 Cases as of " + casesTableDate.getValue());
    	
    	casesTableDate.setDayCellFactory(param -> new DateCell() {
	        @Override
	        public void updateItem(LocalDate date, boolean empty) {
	            super.updateItem(date, empty);
	            LocalDate minDate = LocalDate.of(2020,3,1);
	            LocalDate maxDate = LocalDate.of(2021,7,20);
	            setDisable(date.isAfter(maxDate) || date.isBefore(minDate) );
	        }
	    });
			
    	casesTableList.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> param) {
                return new ListCell<String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);

                        if (empty || item == null) {
         		           // setText(null);
         		        	setStyle(null);
         		        } else {
         		            setText(item);
         		        	setStyle(casesTableSelectedList.contains(item) ? "-fx-background-color: green; -fx-text-fill: white;" : (getIndex() %2 == 0)? "-fx-background-color: lightgray; -fx-text-fill: black;" : "-fx-background-color: white; -fx-text-fill: black;");
         		        }
                    }
                };
            }
        });
    	
    	//Task A2
    	casesGraphSelectedList = new ArrayList<>();
    	casesGraphUnSelectedList = (ArrayList) MyApplication.data.getLocationName().clone();
    	casesGraphUnSelectedList.sort(Comparator.naturalOrder());
    	casesGraphList.setItems(FXCollections.observableArrayList(casesGraphUnSelectedList));
    	casesGraphEndDate.setValue(LocalDate.of(2020,3,2));
    	casesGraphStartDate.setValue(LocalDate.of(2020,3,1));
    	//casesGraphTitle.setText("Number of Confirmed COVID-19 Cases as of " + casesTableDate.getValue());
    	
    	casesGraphEndDate.setDayCellFactory(param -> new DateCell() {
	        @Override
	        public void updateItem(LocalDate date, boolean empty) {
	            super.updateItem(date, empty);
	            LocalDate minDate = casesGraphStartDate.getValue().plusDays(1);
	            LocalDate maxDate = LocalDate.of(2021,7,20);
	            setDisable(date.isAfter(maxDate) || date.isBefore(minDate) );
	        }
	    });
    	casesGraphStartDate.setDayCellFactory(param -> new DateCell() {
	        @Override
	        public void updateItem(LocalDate date, boolean empty) {
	            super.updateItem(date, empty);
	            LocalDate minDate = LocalDate.of(2020,3,1);
	            LocalDate maxDate = LocalDate.of(2021,7,19);
	            if (!casesGraphEndDate.getValue().isAfter(casesGraphStartDate.getValue())) {
	            	casesGraphEndDate.setValue(casesGraphStartDate.getValue().plusDays(1));
	            }
	            setDisable(date.isAfter(maxDate) || date.isBefore(minDate) );
	        }
	    });
    	
    	casesGraphList.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> param) {
                return new ListCell<String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);

                        if (empty || item == null) {
         		           // setText(null);
         		        	setStyle(null);
         		        } else {
         		            setText(item);
         		        	setStyle(casesGraphSelectedList.contains(item) ? "-fx-background-color: green; -fx-text-fill: white;" : (getIndex() %2 == 0)? "-fx-background-color: lightgray; -fx-text-fill: black;" : "-fx-background-color: white; -fx-text-fill: black;");
         		        }
                    }
                };
            }
        });

    	/** Task B1 **/
    	deathsTableSelectedList = new ArrayList<>();
    	deathsTableNotSelectedList = (ArrayList) MyApplication.data.getLocationName().clone();
    	deathsTableNotSelectedList.sort(Comparator.naturalOrder());
    	deathsTableList.setItems(FXCollections.observableArrayList(deathsTableNotSelectedList));
    	deathsTableDate.setValue(LocalDate.of(2020,3,1));
    	updateDeathsTableTitle();
		deathsTableDate.setDayCellFactory(param -> new DateCell() {
	        @Override
	        public void updateItem(LocalDate date, boolean empty) {
	            super.updateItem(date, empty);
	            LocalDate minDate = LocalDate.of(2020,3,1);
	            LocalDate maxDate = LocalDate.of(2021,7,20);
	            setDisable(date.isAfter(maxDate) || date.isBefore(minDate) );
	        }
	    });
		deathsTableList.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> param) {
                return new ListCell<String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
         		           // setText(null);
         		        	setStyle(null);
         		        } else {
         		            setText(item);
         		        	setStyle(deathsTableSelectedList.contains(item) ? "-fx-background-color: green; -fx-text-fill: white;" : (getIndex() %2 == 0)? "-fx-background-color: lightgray; -fx-text-fill: black;" : "-fx-background-color: white; -fx-text-fill: black;");
         		        }
                    }
                };
            }
        });
		
		/** Task B2 **/
		deathsGraphSelectedList = new ArrayList<>();
    	deathsGraphNotSelectedList = (ArrayList) MyApplication.data.getLocationName().clone();
    	deathsGraphNotSelectedList.sort(Comparator.naturalOrder());
    	deathsGraphList.setItems(FXCollections.observableArrayList(deathsGraphNotSelectedList));
    	deathsGraphStartDate.setValue(LocalDate.of(2020,3,1));
    	deathsGraphEndDate.setValue(LocalDate.of(2020,3,2));
		deathsGraphStartDate.setDayCellFactory(param -> new DateCell() {
	        @Override
	        public void updateItem(LocalDate date, boolean empty) {
	            super.updateItem(date, empty);
	            LocalDate minDate = LocalDate.of(2020,3,1);
	            LocalDate maxDate = LocalDate.of(2021,7,19);
	            if (!deathsGraphEndDate.getValue().isAfter(deathsGraphStartDate.getValue())) {
	            	deathsGraphEndDate.setValue(deathsGraphStartDate.getValue().plusDays(1));
	            }
	            setDisable(date.isAfter(maxDate) || date.isBefore(minDate) );
	        }
	    });
		deathsGraphEndDate.setDayCellFactory(param -> new DateCell() {
	        @Override
	        public void updateItem(LocalDate date, boolean empty) {
	            super.updateItem(date, empty);
	            LocalDate minDate = deathsGraphStartDate.getValue().plusDays(1);
	            LocalDate maxDate = LocalDate.of(2021,7,20);
	            if (!deathsGraphEndDate.getValue().isAfter(deathsGraphStartDate.getValue())) {
	            	deathsGraphStartDate.setValue(deathsGraphEndDate.getValue().minusDays(1));
	            }
	            setDisable(date.isAfter(maxDate) || date.isBefore(minDate) );
	        }
	    });
		deathsGraphList.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> param) {
                return new ListCell<String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
         		           // setText(null);
         		        	setStyle(null);
         		        } else {
         		            setText(item);
         		        	setStyle(deathsGraphSelectedList.contains(item) ? "-fx-background-color: green; -fx-text-fill: white;" : (getIndex() %2 == 0)? "-fx-background-color: lightgray; -fx-text-fill: black;" : "-fx-background-color: white; -fx-text-fill: black;");
         		        }
                    }
                };
            }
        });
		
    	
    	// Task C1
    	if(!check) {
    		check = true;
    		
    		selectedCountries = new ArrayList<String>();
    		unselectedCountries = (ArrayList) MyApplication.data.getLocationName().clone();
    		unselectedCountries.sort(Comparator.naturalOrder());
    		
    		updateListView(2);
    		
    		/*
    		vaccinationTableList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>() {
    			@Override
    			public void changed(ObservableValue ov, Object t, Object t1) {
    				//System.out.println("invoke changes");
    				if (t1 == null)
    					return;
    				String currentSelect = t1.toString();
    				
    				
    				//if it is selected or not, when there is only one selected, sometimes cannot delete, 
    				//when select one and try to delete, does not call this method
    				if(selectedCountries != null && selectedCountries.contains(currentSelect)) {
    					unselectedCountries.add(currentSelect);
    					selectedCountries.remove(currentSelect);
    					unselectedCountries.sort(Comparator.naturalOrder());
    					if(selectedCountries.size() == 0) 
    						updateTableView();
    				}
    				else if(unselectedCountries != null && unselectedCountries.contains(currentSelect)) {
    					selectedCountries.add(currentSelect);
    					unselectedCountries.remove(currentSelect);
    					selectedCountries.sort(Comparator.naturalOrder());
//    					if(tableDateOfInterest != null) 
//    						updateTableView();
    				}
    				updateListView(1);
    				

    			}
    		});
    		
    	*/
    		
    		vaccinationTableList.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
                @Override
                public ListCell<String> call(ListView<String> param) {
                    return new ListCell<String>() {
                        @Override
                        protected void updateItem(String item, boolean empty) {
                            super.updateItem(item, empty);

                            if (empty || item == null) {
             		           // setText(null);
             		        	setStyle(null);
             		        } else {
             		            setText(item);
             		        	setStyle(selectedCountries.contains(item) ? "-fx-background-color: green; -fx-text-fill: white;" : (getIndex() %2 == 0)? "-fx-background-color: lightgray; -fx-text-fill: black;" : "-fx-background-color: white; -fx-text-fill: black;");
             		        }
                        }
                    };
                }
            });
    		
    		
    		vaccinationTableCountry.setCellValueFactory(new PropertyValueFactory<>("location"));
    		vaccinationTableTotal.setCellValueFactory(new PropertyValueFactory<>("fullyVaccinated"));
    		vaccinationTablePer1M.setCellValueFactory(new PropertyValueFactory<>("rateOfVaccination"));
    		
    		vaccinationTableDate.setValue(LocalDate.of(2020,3,1));
    		//String strDate = String.format("%d/%d/%d",ld.getMonthValue(),ld.getDayOfMonth(),ld.getYear());
        	tableDateOfInterest = new Date("5/1/2020");
    		vaccinationTableDate.setDayCellFactory(param -> new DateCell() {
    	        @Override
    	        public void updateItem(LocalDate date, boolean empty) {
    	            super.updateItem(date, empty);
    	            LocalDate minDate = LocalDate.of(2020,3,1);
    	            LocalDate maxDate = LocalDate.of(2021,7,20);
    	            setDisable(date.isAfter(maxDate) || date.isBefore(minDate) );
    	        }
    	    });
    		
    		
    		
    		//Graph part
    		selectedGraphCountries = new ArrayList<String>();
    		unselectedGraphCountries = (ArrayList) MyApplication.data.getLocationName().clone();
    		unselectedGraphCountries.sort(Comparator.naturalOrder());
    		
    		updateGraphListView(2);
    		
    		vaccinationGraphStartDate.setValue(LocalDate.of(2020,3,1));
    		//String strDate = String.format("%d/%d/%d",ld.getMonthValue(),ld.getDayOfMonth(),ld.getYear());
        	rovStartDate = LocalDate.of(2020,3,1);
        	vaccinationGraphEndDate.setValue(LocalDate.of(2020,3,2));
    		//String strDate = String.format("%d/%d/%d",ld.getMonthValue(),ld.getDayOfMonth(),ld.getYear());
        	rovEndDate = LocalDate.of(2020,3,2);
    		vaccinationGraphStartDate.setDayCellFactory(param -> new DateCell() {
    	        @Override
    	        public void updateItem(LocalDate date, boolean empty) {
    	            super.updateItem(date, empty);
//    	            LocalDate minDate = LocalDate.of(2020,3,1);
//    	            LocalDate maxDate = LocalDate.of(2021,7,19);
//    	            setDisable(date.isAfter(maxDate) || date.isBefore(minDate) );
    	            
    	            LocalDate minDate = LocalDate.of(2020,3,1);
    	            LocalDate maxDate = LocalDate.of(2021,7,19);
    	            if (!vaccinationGraphEndDate.getValue().isAfter(vaccinationGraphStartDate.getValue())) {
    	            	vaccinationGraphEndDate.setValue(vaccinationGraphStartDate.getValue().plusDays(1));
    	            }
    	            setDisable(date.isAfter(maxDate) || date.isBefore(minDate) );
    	        }
    	    });
    		vaccinationGraphEndDate.setDayCellFactory(param -> new DateCell() {
    	        @Override
    	        public void updateItem(LocalDate date, boolean empty) {
    	            super.updateItem(date, empty);
    	            LocalDate minDate = vaccinationGraphStartDate.getValue().plusDays(1);
    	            LocalDate maxDate = LocalDate.of(2021,7,20);
    	            setDisable(date.isAfter(maxDate) || date.isBefore(minDate) );
    	        }
    	    });
    		
    		
    		vaccinationGraphList.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
                @Override
                public ListCell<String> call(ListView<String> param) {
                    return new ListCell<String>() {
                        @Override
                        protected void updateItem(String item, boolean empty) {
                            super.updateItem(item, empty);

                            if (empty || item == null) {
             		           // setText(null);
             		        	setStyle(null);
             		        } else {
             		            setText(item);
             		        	setStyle(selectedGraphCountries.contains(item) ? "-fx-background-color: green; -fx-text-fill: white;" : (getIndex() %2 == 0)? "-fx-background-color: lightgray; -fx-text-fill: black;" : "-fx-background-color: white; -fx-text-fill: black;");
             		        }
                        }
                    };
                }
            });
    		
    		/*
    		vaccinationGraphList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>() {
    			@Override
    			public void changed(ObservableValue ov, Object t, Object t1) {
    				//System.out.println("invoke changes");
    				if (t1 == null)
    					return;
    				String currentSelect = t1.toString();
    				
    				
    				//if it is selected or not, when there is only one selected, sometimes cannot delete, 
    				//when select one and try to delete, does not call this method
    				if(selectedGraphCountries != null && selectedGraphCountries.contains(currentSelect)) {
    					unselectedGraphCountries.add(currentSelect);
    					selectedGraphCountries.remove(currentSelect);
    					unselectedGraphCountries.sort(Comparator.naturalOrder());
    					if(selectedGraphCountries.size() == 0) 
    						updateGraphView();
    				}
    				else if(unselectedGraphCountries != null && unselectedGraphCountries.contains(currentSelect)) {
    					selectedGraphCountries.add(currentSelect);
    					unselectedGraphCountries.remove(currentSelect);
    					selectedGraphCountries.sort(Comparator.naturalOrder());
//    					if(tableDateOfInterest != null) 
//    						updateTableView();
    				}
    				updateGraphListView(1);
    				

    			}
    		});
    		*/
    	}
    }
    
    /**
     * Update the list view when users selecting a places for vaccination graph section
     * @param event The event that trigger the function, which is clicking a place in the list view
     */
    @FXML
    public void vaccinationGraphListUpdate(MouseEvent event) {
//    	if (t1 == null)
//			return;
    	//System.out.println("GraphListUpdate");
		String currentSelect = vaccinationGraphList.getSelectionModel().getSelectedItem().toString();
		
		
		//if it is selected or not, when there is only one selected, sometimes cannot delete, 
		//when select one and try to delete, does not call this method
		if(selectedGraphCountries != null && selectedGraphCountries.contains(currentSelect)) {
			unselectedGraphCountries.add(currentSelect);
			selectedGraphCountries.remove(currentSelect);
			unselectedGraphCountries.sort(Comparator.naturalOrder());
			if(selectedGraphCountries.size() == 0) {
				clearSelect = true;
				updateGraphView();
				clearSelect = false;
			}
				
		}
		else if(unselectedGraphCountries != null && unselectedGraphCountries.contains(currentSelect)) {
			selectedGraphCountries.add(currentSelect);
			unselectedGraphCountries.remove(currentSelect);
			selectedGraphCountries.sort(Comparator.naturalOrder());
//			if(tableDateOfInterest != null) 
//				updateTableView();
		}
		updateGraphListView(1);
    }
    
    /**
     * Update the list view when users selecting a places for vaccination table section
     * @param event The event that trigger the function, which is clicking a place in the list view
     */
    
    @FXML
    public void vaccinationTableListUpdate(MouseEvent event) {
//	    if (t1 == null)
//			return;
    	//System.out.println("TableListUpdate");
	    String currentSelect = vaccinationTableList.getSelectionModel().getSelectedItem().toString();
		
		
		//if it is selected or not, when there is only one selected, sometimes cannot delete, 
		//when select one and try to delete, does not call this method
		if(selectedCountries != null && selectedCountries.contains(currentSelect)) {
			unselectedCountries.add(currentSelect);
			selectedCountries.remove(currentSelect);
			unselectedCountries.sort(Comparator.naturalOrder());
			if(selectedCountries.size() == 0) 
				updateTableView();
		}
		else if(unselectedCountries != null && unselectedCountries.contains(currentSelect)) {
			selectedCountries.add(currentSelect);
			unselectedCountries.remove(currentSelect);
			selectedCountries.sort(Comparator.naturalOrder());
	//		if(tableDateOfInterest != null) 
	//			updateTableView();
		}
		updateListView(1);
    }
    
    /**
     * helper function to update the table view for the vaccination graph section
     */
    public void updateTableView() {
    	//vaccinationTable.getItems.clear();
    	ObservableList<RateOfVaccinationDateSpec> tableData = FXCollections.observableArrayList();
    	if(selectedCountries.size() == 0 || tableDateOfInterest == null) {
    		vaccinationTableTitle.setText("Rate of Vaccination against COVID-19");
    	}
    	else {
    		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");   
    		String titleForDate = "Rate of Vaccination against COVID-19 as of " + sdf.format(tableDateOfInterest);
    		vaccinationTableTitle.setText(titleForDate);
    		for(int i = 0; i<selectedCountries.size(); i++) {
    			String currentCountry = selectedCountries.get(i);
    			RateOfVaccination rate;
    			if(currentCountry != null)
    				rate = DataAnalysis.data.getRateOfVaccinationLocSpecific(currentCountry);
    			else 
    				return;
    			long fullyVaccinatedValue = (rate.getFullyVaccinatedDateSpecific(tableDateOfInterest)==null)? (long)-1: rate.getFullyVaccinatedDateSpecific(tableDateOfInterest);
    			double rateOfInterestValue = rate.getRateOfVaccinationDateSpecific(tableDateOfInterest)== null ?-1.0:rate.getRateOfVaccinationDateSpecific(tableDateOfInterest);
    			tableData.add(new RateOfVaccinationDateSpec(rate.getName(),fullyVaccinatedValue, 
    					rateOfInterestValue));
    		}
    		
    	}
    	vaccinationTable.setItems(tableData);
    }
    
    /**
     * update the List view in the vaccination table section
     * @param index 0:call when searching 1:call when adding selecting countries 2:call when initializing
     */
    public void updateListView(int index) {
    	//System.out.println("updateListView");
    	vaccinationTableList.getItems().clear();
    	ArrayList<String> list = new ArrayList<String>();
    	if(index == 0) {
    		if (currentSearch == null || currentSearch == "") {
        		ArrayList<String> merged = new ArrayList<>();
            	merged.addAll(selectedCountries);
            	merged.addAll(unselectedCountries);
            	//System.out.println("-notsearched-");
            	vaccinationTableList.setItems(FXCollections.observableArrayList(merged));
        	} else {
        		ArrayList<String> newSelectedList = new ArrayList<>();
        		ArrayList<String> newNotSelectedList = new ArrayList<>();
        		currentSearch = currentSearch.toLowerCase();
        		for (String s : selectedCountries) {
        			if (s.toLowerCase().contains(currentSearch))
        				newSelectedList.add(s);
        		}
        		for (String s : unselectedCountries) {
        			if (s.toLowerCase().contains(currentSearch))
        				newNotSelectedList.add(s);
        		}
        		newNotSelectedList.sort(Comparator.naturalOrder());
        		newSelectedList.sort(Comparator.naturalOrder());
        		ArrayList<String> merged = new ArrayList<>();
            	merged.addAll(newSelectedList);
            	merged.addAll(newNotSelectedList);
            	//System.out.println("-searched-");
            	//vaccinationGraphList.getItems().clear();
            	vaccinationTableList.setItems(FXCollections.observableArrayList(merged));
            	//for (String s : deathsTableList.getItems())
            	//	System.out.println(s);
        	}
    		return;
    		
    	}
    	else if(index == 1) {
    		//rememver to add color
    		list.addAll(selectedCountries);
    		list.addAll(unselectedCountries);
    	}
    	else if(index == 2) {
    		//initialize
    		list.addAll(unselectedCountries);
    		list.sort(Comparator.naturalOrder());
    	}
    	
        ObservableList<String> combox2 = FXCollections.observableArrayList(list);
        
        vaccinationTableList.setItems(combox2);

    }
    
}

