package control;

import java.util.ArrayList;

import model.KuechenApp;
import model.Rezept;

public class KochController {
	
	private KuechenApp kuechenApp;

	private BestandsController bestandsController;

	private EinkaufsController einkaufsController;

	private RezeptController rezeptController;

	private ImportExportController importExportController;

	private IOController ioController;
	
	
	//2Konstruktor um zu testen (export)
	public KochController(ArrayList<Rezept> rezepte) {
		kuechenApp = new KuechenApp(rezepte);
		bestandsController = new BestandsController(this);
		einkaufsController = new EinkaufsController(this);
		rezeptController =  new RezeptController(this);
		importExportController = new ImportExportController(this);
		ioController = new IOController(this);
	}
	
	public KochController() {
		kuechenApp = new KuechenApp();
		bestandsController = new BestandsController(this);
		einkaufsController = new EinkaufsController(this);
		rezeptController =  new RezeptController(this);
		importExportController = new ImportExportController(this);
		ioController = new IOController(this);
	}

	public RezeptController getRezeptController() {
		return rezeptController;
	}
	
	public KuechenApp getKuechenApp() {
		return kuechenApp;
	}

	public void setKuechenApp(KuechenApp kuechenApp) {
		this.kuechenApp = kuechenApp;
	}

	public BestandsController getBestandsController() {
		return bestandsController;
	}

	public void setBestandsController(BestandsController bestandsController) {
		this.bestandsController = bestandsController;
	}

	public EinkaufsController getEinkaufsController() {
		return einkaufsController;
	}

	public void setEinkaufsController(EinkaufsController einkaufsController) {
		this.einkaufsController = einkaufsController;
	}

	public ImportExportController getImportExportController() {
		return importExportController;
	}

	public void setImportExportController(
			ImportExportController importExportController) {
		this.importExportController = importExportController;
	}

	public IOController getIOController() {
		return ioController;
	}

	public void setIOController(IOController iOController) {
		this.ioController = iOController;
	}

	public void setRezeptController(RezeptController rezeptController) {
		this.rezeptController = rezeptController;
	}



}
