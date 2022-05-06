package shared;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Random;

import weka.core.Instances;
import weka.core.converters.CSVLoader;
import weka.clusterers.ClusterEvaluation;
import weka.clusterers.EM;
	
public class Clustering {


	public static void main(String args[]) throws Exception{
		
		//load data
		//Instances data = new Instances(new BufferedReader(new FileReader("data/bank-data.arff")));
		CSVLoader loader = new CSVLoader();
		loader.setSource(new File("ClassificationData/projet.csv"));
		Instances data = loader.getDataSet();
		System.out.println(data.toString());
		// new instance of clusterer
		EM model = new EM();
		// build the clusterer
		model.buildClusterer(data);
		System.out.println(model);
		
		double logLikelihood = ClusterEvaluation.crossValidateModel(model, data, 13, new Random(1));
		System.out.println(logLikelihood);


	}

}
