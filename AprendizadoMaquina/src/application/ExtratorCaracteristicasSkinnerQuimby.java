package application;
import java.io.File;
import java.io.FileOutputStream;

import org.opencv.core.Mat;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;

public class ExtratorCaracteristicasSkinnerQuimby {
	
public static double[] extraiCaracteristicas(File f, boolean exibeImagem) {
		
		double[] caracteristicas = new double[9];
		
		double roxoCamisaSkinner = 0;
		double azulRoupaSkinner = 0;
		double laranjaGravataSkinner = 0;
		double cabeloSkinner = 0;
		double azulRoupaQuimby = 0;
		double azulGravataQuimby = 0;
		double brancoFaixaQuimby = 0; 
		double cabeloQuimby = 0;
		
		
		
		Image img = new Image(f.toURI().toString());
		PixelReader pr = img.getPixelReader();
		
		Mat imagemOriginal = Imgcodecs.imread(f.getPath());
        Mat imagemProcessada = imagemOriginal.clone();
		
		int w = (int)img.getWidth();
		int h = (int)img.getHeight();
		
		
		for(int i=0; i<h; i++) {
			for(int j=0; j<w; j++) {
				
				Color cor = pr.getColor(j,i);
				
				double r = cor.getRed()*255; 
				double g = cor.getGreen()*255;
				double b = cor.getBlue()*255;
				
				
				if(i>(h/3) && isRoxoCamisaSkinner(r, g, b)) {
					roxoCamisaSkinner++;
					imagemProcessada.put(i, j, new double[]{0, 255, 128});
				}
				if(i>(h/3) && isAzulRoupaSkinner(r, g, b)) {
					azulRoupaSkinner++;
					imagemProcessada.put(i, j, new double[]{0, 255, 128});
				}
				if(i>(h/3) && isLaranjaGravataSkinner(r, g, b)) {
					laranjaGravataSkinner++;
					imagemProcessada.put(i, j, new double[]{0, 255, 128});
				}
				if(i<(h/2) && isCabeloSkinner(r, g, b)) {
					cabeloSkinner++;
					imagemProcessada.put(i, j, new double[]{0, 255, 128});
				}
				
				if(i>(h/3) && isAzulRoupaQuimby(r, g, b)) {
					azulRoupaQuimby++;
					imagemProcessada.put(i, j, new double[]{0, 255, 255});
				}
				if(i>(h/3) && isAzulGravataQuimby(r, g, b)) {
					azulGravataQuimby++;
					imagemProcessada.put(i, j, new double[]{0, 255, 255});
				}
				if(i>(h/3) && i<(h/2 + h/3) && isBrancoFaixaQuimby(r, g, b)) {
					brancoFaixaQuimby++;
					imagemProcessada.put(i, j, new double[]{0, 255, 255});
				}
				if(i<(h/3) && isCabeloQuimby(r, g, b)) {
					cabeloQuimby++;
					imagemProcessada.put(i, j, new double[]{0, 0, 255});
				}
				
							
				
			}
		}
		
		// Normaliza as características pelo número de pixels totais da imagem para %
		roxoCamisaSkinner = (roxoCamisaSkinner /(w*h))*100;
		azulRoupaSkinner = (azulRoupaSkinner /(w*h))*100;
		laranjaGravataSkinner = (laranjaGravataSkinner /(w*h))*100;
		cabeloSkinner = (cabeloSkinner /(w*h))*100;
		azulRoupaQuimby = (azulRoupaQuimby /(w*h))*100;
		azulGravataQuimby = (azulGravataQuimby /(w*h))*100;
		brancoFaixaQuimby = (brancoFaixaQuimby /(w*h))*100; 
		cabeloQuimby = (cabeloQuimby /(w*h))*100;
        
        caracteristicas[0] = roxoCamisaSkinner;
        caracteristicas[1] = azulRoupaSkinner;
        caracteristicas[2] = laranjaGravataSkinner;
        caracteristicas[3] = cabeloSkinner;
        caracteristicas[4] = azulRoupaQuimby;
        caracteristicas[5] = azulGravataQuimby;
        caracteristicas[6] = brancoFaixaQuimby;
        caracteristicas[7] = cabeloQuimby;
        //APRENDIZADO SUPERVISIONADO - JÁ SABE QUAL A CLASSE NAS IMAGENS DE TREINAMENTO
        caracteristicas[8] = f.getName().charAt(0)=='p'?0:1;
		if(exibeImagem) {
			HighGui.imshow("Imagem original", imagemOriginal);
	        HighGui.imshow("Imagem processada", imagemProcessada);
	        
	        HighGui.waitKey(0);
		}
		
		return caracteristicas;
	}
	
	//Caracteristicas Skinner
	public static boolean isRoxoCamisaSkinner(double r, double g, double b) {
		 if (r >= 130 && r <= 190 &&  g >= 90 && g <= 130 &&  b >= 150 && b <= 230) {                       
         	return true;
         }
		 return false;
	}
	
	public static boolean isAzulRoupaSkinner(double r, double g, double b) {
		 if (r >= 4 && r <= 60 &&  g >= 90 && g <= 110 &&  b >= 110 && b <= 180) {                       
        	return true;
        }
		 return false;
	}
	public static boolean isLaranjaGravataSkinner(double r, double g, double b) {
		if (r >= 180 && r <= 215 &&  g >= 110 && g <= 130 &&  b >= 80 && b <= 110) {                       
			return true;
		}
		return false;
	}
	public static boolean isCabeloSkinner(double r, double g, double b) {
		if (r >= 95 && r <= 140 &&  g >= 80 && g <= 125 &&  b >= 70 && b <= 120) {                       
			return true;
		}
		return false;
	}

	
	//Caracteristicas Quimby
	public static boolean isAzulRoupaQuimby(double r, double g, double b) {
		if (r >= 49 && r <= 95 &&  g >= 15 && g <= 95 &&  b >= 85 && b <= 150) {                       
			return true;
		}
		return false;
	}
	public static boolean isAzulGravataQuimby(double r, double g, double b) {
		if (r >= 30 && r <= 70 &&  g >= 50 && g <= 115 &&  b >= 125 && b <= 140) {                       
			return true;
		}
		return false;
	}
	public static boolean isBrancoFaixaQuimby(double r, double g, double b) {
		if (r >= 200 && r <= 253 &&  g >= 200 && g <= 253 &&  b >= 200 && b <= 253) {                       
			return true;
		}
		return false;
	}
	public static boolean isCabeloQuimby(double r, double g, double b) {
		if (r >= 40 && r <= 140 &&  g >=20 && g <= 60 &&  b >= 0 && b <= 20) {                       
			return true;
		}
		return false;
	}


	public static void extrair(boolean exibeImagem) {
				
	    // Cabeçalho do arquivo Weka
		String exportacao = "@relation caracteristicas\n\n";
		exportacao += "@attribute roxo_camisa_skinner real\n";
		exportacao += "@attribute azul_roupa_skinner real\n";
		exportacao += "@attribute laranja_gravata_skinner real\n";
		exportacao += "@attribute cinza_cabelo_skinner real\n";
		exportacao += "@attribute azul_roupa_quimby real\n";
		exportacao += "@attribute azul_gravata_quimby real\n";
		exportacao += "@attribute branco_faixa_quimby real\n";
		exportacao += "@attribute marrom_cabelo_quimby real\n";
		exportacao += "@attribute classe {Skinner, Quimby}\n\n";
		exportacao += "@data\n";
	        
	    // Diretório onde estão armazenadas as imagens
	    File diretorio = new File("src\\img");
	    File[] arquivos = diretorio.listFiles();
	    
        // Definição do vetor de características
        double[][] caracteristicas = new double[1439][9];
        
        // Percorre todas as imagens do diretório
        int cont = -1;
        for (File imagem : arquivos) {
        	cont++;
        	caracteristicas[cont] = extraiCaracteristicas(imagem, exibeImagem);
        	
        	String classe = caracteristicas[cont][8] == 0 ?"Skinner":"Quimby";
        	
        	System.out.println("Roxo Camisa Skinner: " + caracteristicas[cont][0] 
            		+ " - Azul Roupa Skinner: " + caracteristicas[cont][1] 
            		+ " - Laranja Gravata Skinner: " + caracteristicas[cont][2] 
            		+ " - Cinza Cabelo Skinner: " + caracteristicas[cont][3] 
            		+ " - Azul Roupa Quimby: " + caracteristicas[cont][4] 
            		+ " - Azul Gravata Quimby: " + caracteristicas[cont][5] 
       				+ " - Branco Faixa Quimby: " + caracteristicas[cont][6] 
					+ " - Marrom Cabelo Quimby: " + caracteristicas[cont][7] 
            		+ " - Classe: " + classe);
        	
        	exportacao += caracteristicas[cont][0] + "," 
                    + caracteristicas[cont][1] + "," 
        		    + caracteristicas[cont][2] + "," 
                    + caracteristicas[cont][3] + "," 
        		    + caracteristicas[cont][4] + "," 
                    + caracteristicas[cont][5] + "," 
                    + caracteristicas[cont][6] + "," 
                    + caracteristicas[cont][7] + "," 
                    + classe + "\n";
        }
        
     // Grava o arquivo ARFF no disco
        try {
        	File arquivo = new File("caracteristicas.arff");
        	FileOutputStream f = new FileOutputStream(arquivo);
        	f.write(exportacao.getBytes());
        	f.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	
}