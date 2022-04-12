<div align="center">
  
  # HuffmanFileCompressor
  ![Build status](https://img.shields.io/circleci/build/gh/lluiscamino/HuffmanFileCompressor?token=cd57878d0d901abdb75ae269e354b403a455ac01)
  ![Project license](https://img.shields.io/github/license/lluiscamino/HuffmanFileCompressor)
  ![Open issues](https://img.shields.io/github/issues/lluiscamino/HuffmanFileCompressor)
</div>

Open source file compression and decompression program based on [**Huffman's coding**](https://en.wikipedia.org/wiki/Huffman_coding).

Developed as a project for the Advanced Algorithms course at the University of the Balearic Islands.

## Features
* Works on all modern OS.
* File compression and decompression.
* Intuitive graphical user interface (GUI).
* Visualization of Huffman trees.

## How does it work

### Compressor
The compressor or encoder first reads the input file and constructs a Huffman tree based on the frequency of each byte in the file. For example, for an input file with the sentence "AABBBCD", it would create the following tree:

<div align="center">
  
  ![Huffman tree example for sentence "AABBBCD"](https://raw.githubusercontent.com/lluiscamino/HuffmanFileCompressor/b508c608083116c4312677e7c71904046bf86538/tree_example.svg?token=AFPS2QBUJRIYPZZZC77FHYDCKXNFI)
</div>

Then, it encodes the input tree at the start of the output file, so that it can be decompressed in the future.

Finally, it builds a map between the input file bytes and their Huffman encoding and it writes the corresponding encoded bits into the output file. For example, the map for the input file with contents "AABBBCD" would be: `{A=10, B=0, C=110, D=111}`.

### Decompressor

The decompressor or decoder first reads the compressed file and builds the Huffman tree from its encoded representation.

Then, it uses this tree while reading the compressed file to write the decompressed file.

## How to use it

The program contains an intuitive graphical user interface that allows compressing and decompressing files easily.

To compress or decompress a file, just select it on the file selector and then click on one of the two buttons located at the right menu, as shown in the next screenshot.

<div align="center">
  
![Program GUI](https://github.com/lluiscamino/HuffmanFileCompressor/blob/main/gui_screenshot.png?raw=true)
</div>

After having compressed or decompressed a file, it is possible to see the Huffman tree and codes. The Huffman tree can also be saved as a SVG image.
