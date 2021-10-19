package application;

import java.io.File;
import java.text.DecimalFormat;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

public class AprendizadoController {
	@FXML
	public ImageView imageView;
	public Label b0 = new Label();
	public Label b1 = new Label();
	public Label b2 = new Label();
	public Label b3 = new Label();
	public Label b4 = new Label();
	public Label h0 = new Label();
	public Label h1 = new Label();
	public Label h2 = new Label();
	public Label h3 = new Label();
	public Label h4 = new Label();
	public Label naiveBayesBart = new Label();
	public Label naiveBayesHomer = new Label();
	public ToggleButton exibeImagem = new ToggleButton();
	public CheckBox aval1 = new CheckBox();
	
	private double[] caracteristicasImgSel = {0,0,0,0,0,0};
	private DecimalFormat df = new DecimalFormat("##0.0000");
	private boolean avalia1 = false;
	private String personagem1 = "Bart";
	private String personagem2 = "Homer";
	
	
	
	@FXML
	public void extrair() {
		if(!aval1.isSelected())
			ExtratorCaracteristicas.extrair(exibeImagem.isSelected());
		else
			ExtratorCaracteristicasSkinnerQuimby.extrair(exibeImagem.isSelected());
	}
	
	@FXML
	public void teste() {
		if(!aval1.isSelected()) {
			personagem1 = "Bart";
			personagem2 = "Homer";
			b1.setText("Laranja Camisa: ");
			b2.setText("Azul Calção: ");
			b3.setText("Azul Sapato: ");
			b4.setText("");
			h1.setText("Azul Calça: ");
			h2.setText("Marrom Boca: ");
			h3.setText("Preto Sapato: ");
			h4.setText("");
		}else {
			personagem1 = "Skinner";
			personagem2 = "Quimby";
			
			b1.setText("Roxo Camisa: ");
			b2.setText("Azul Roupa: ");
			b3.setText("Laranja Gravata: ");
			b4.setText("Cinza Cabelo: ");
			h1.setText("Azul Roupa: ");
			h2.setText("Azul Gravata: ");
			h3.setText("Branco Faixa: ");
			h4.setText("Marrom Cabelo: ");
		}
		b0.setText(personagem1);
		h0.setText(personagem2);
	}
	
	@FXML
	public void classificar() {
		//*********Naive Bayes
		double[] nb = AprendizagemBayesiana.naiveBayes(caracteristicasImgSel);
		naiveBayesBart.setText(personagem1+": "+df.format(nb[0])+"%");
		naiveBayesHomer.setText(personagem2+": "+df.format(nb[1])+"%");
	}
	
	
	@FXML
	public void selecionaImagem() {
		File f = buscaImg();
		if(f != null) {
			Image img = new Image(f.toURI().toString());
			imageView.setImage(img);
			imageView.setFitWidth(img.getWidth());
			imageView.setFitHeight(img.getHeight());
			if(!aval1.isSelected()) {
				personagem1 = "Bart";
				personagem2 = "Homer";
				caracteristicasImgSel = ExtratorCaracteristicas.extraiCaracteristicas(f, false);
				b1.setText("Laranja Camisa: "+df.format(caracteristicasImgSel[0]));
				b2.setText("Azul Calção: "   +df.format(caracteristicasImgSel[1]));
				b3.setText("Azul Sapato: "   +df.format(caracteristicasImgSel[2]));
				h1.setText("Azul Calça: "    +df.format(caracteristicasImgSel[3]));
				h2.setText("Marrom Boca: "   +df.format(caracteristicasImgSel[4]));
				h3.setText("Preto Sapato: "  +df.format(caracteristicasImgSel[5]));
			}else {
				personagem1 = "Skinner";
				personagem2 = "Quimby";
				
				caracteristicasImgSel = ExtratorCaracteristicasSkinnerQuimby.extraiCaracteristicas(f, false);
				b1.setText("Roxo Camisa: "+df.format(caracteristicasImgSel[0]));
				b2.setText("Azul Roupa: "   +df.format(caracteristicasImgSel[1]));
				b3.setText("Laranja Gravata: "   +df.format(caracteristicasImgSel[2]));
				b4.setText("Cinza Cabelo: "+df.format(caracteristicasImgSel[3]));
				h1.setText("Azul Roupa: "    +df.format(caracteristicasImgSel[4]));
				h2.setText("Azul Gravata: "   +df.format(caracteristicasImgSel[5]));
				h3.setText("Branco Faixa: "  +df.format(caracteristicasImgSel[6]));
				h4.setText("Marrom Cabelo: "  +df.format(caracteristicasImgSel[7]));
			}
		}
	}
	
	private File buscaImg() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new 
				   FileChooser.ExtensionFilter(
						   "Imagens", "*.jpg", "*.JPG", 
						   "*.png", "*.PNG", "*.gif", "*.GIF", 
						   "*.bmp", "*.BMP")); 	
		 fileChooser.setInitialDirectory(new File("src/imagens"));
		 File imgSelec = fileChooser.showOpenDialog(null);
		 try {
			 if (imgSelec != null) {
			    return imgSelec;
			 }
		 } catch (Exception e) {
			e.printStackTrace();
		 }
		 return null;
	}
	
}
