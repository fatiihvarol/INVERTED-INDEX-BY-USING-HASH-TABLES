import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Locale;

@SuppressWarnings("unused")
public class Management {

	SingleLinkedList SSL;
	private HashTable hashTable = new HashTable();

	public String[] Run(String[] words) throws IOException {// Stop Wordslerin çıkarılması. Kelimelerin hash table için
															// atılması.

		String stopWordLine = "";
		String txt = "";
		Boolean flag = true;

		BufferedReader br = new BufferedReader(new FileReader("stop_words_en.txt"));
		while ((stopWordLine = br.readLine()) != null) {
			txt += stopWordLine + " ";
		}
		String[] stop_words = txt.split(" ");

		for (int i = 0; i < words.length; i++) {
			flag = true;

			for (int j = 0; j < stop_words.length; j++) {

				if (words[i] != null && words[i].equals(stop_words[j])) {
					words[i] = null;
					flag = false;
				}

			}
			if (flag) {

//				if (!hashTable.isContainsLP(words[i])) {
//					hashTable.put(hashTable.PAF(words[i]), ConvertNode(words[i]));	
//				}

				if (!hashTable.isContainsDH(words[i])) {
					hashTable.put(hashTable.PAF(words[i]), ConvertNode(words[i]));
				}

				br.close();
			}
		}
		return words;
	}

	public Node ConvertNode(String word) {// Single Linked Listi yapabilmem için bu dönüşümü kullandım.
		Node value = new Node(word);
		return value;
	}

	public void ReadFile() throws IOException {// Dosyaları okuma ve RUN forSSL sayesinde hash table içine yerleştirme.
		File dir = new File("bbc");
		File folder;
		String txt = "";
		BufferedReader br = null;
		String fileName = "";
		String folderName = "";

		for (File file : dir.listFiles()) {
			folderName = file.getName();
			folder = new File("bbc\\" + folderName);
			for (File file1 : folder.listFiles()) {
				br = new BufferedReader(new FileReader(file1));
				String strLine = "";
				while ((strLine = br.readLine()) != null) {
					txt += strLine.toLowerCase(Locale.ENGLISH) + " ";
				}
				fileName = file1.getName();
				folderName = file.getName();
				txt = txt.replaceAll(System.getProperty("line.separator"), "");

				hashTable.forSLL(Run(Delimiters(txt)), folderName + "/" + fileName);
				txt = "";
			}

		}

		br.close();

		// hashTable.printhash();
		hashTable.Search1000();
		// hashTable.Search();
		// System.out.println("COLLUSION: " +hashTable.getCollision());

	}

	public String[] Delimiters(String text) {// Delimitersların split edilmesi.

		String DELIMITERS = "[-+=" + " " + // space
				"\r\n " + // carriage return line fit
				"1234567890" + // numbers
				"’'\"" + // apostrophe
				"(){}<>\\[\\]" + // brackets
				":" + // colon
				"," + // comma
				"‒–—―" + // dashes
				"…" + // ellipsis
				"!" + // exclamation mark
				"." + // full stop/period
				"«»" + // guillemets
				"-‐" + // hyphen
				"?" + // question mark
				"‘’“”" + // quotation marks
				";" + // semicolon
				"/" + // slash/stroke
				"⁄" + // solidus
				"␠" + // space?
				"·" + // interpunct
				"&" + // ampersand
				"@" + // at sign
				"*" + // asterisk
				"\\" + // backslash
				"•" + // bullet
				"^" + // caret
				"¤¢$€£¥₩₪" + // currency
				"†‡" + // dagger
				"°" + // degree
				"¡" + // inverted exclamation point
				"¿" + // inverted question mark
				"¬" + // negation
				"#" + // number sign (hashtag)
				"№" + // numero sign ()
				"%‰‱" + // percent and related signs
				"¶" + // pilcrow
				"′" + // prime
				"§" + // section sign
				"~" + // tilde/swung dash
				"¨" + // umlaut/diaeresis
				"_" + // underscore/understrike
				"|¦" + // vertical/pipe/broken bar
				"⁂" + // asterism
				"☞" + // index/fist
				"∴" + // therefore sign
				"‽" + // interrobang
				"※" + // reference mark
				"]";

		String[] splitted = text.split(DELIMITERS);
		String line = "";
		for (int i = 0; i < splitted.length; i++) {
			line += splitted[i].toLowerCase() + " ";

		}
		line = line.trim().replaceAll("^ +| +$|( )+", "$1");
		String[] split = line.split(" ");
		return split;
	}

}
