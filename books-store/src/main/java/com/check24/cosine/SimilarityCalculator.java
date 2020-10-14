package com.check24.cosine;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.lang.Math;

@Component
public class SimilarityCalculator {

    /**
     *
     * @param v1 first vector
     * @param v2 second vector
     * @return throws {@link InvalidDataException} if the vectors are invalid
     */
    private void validateVectors(Integer[] v1, Integer[] v2) throws InvalidDataException {
        if (null == v1 || null == v2) {
            throw new InvalidDataException("At least one of the vectors is null. V1 = "+v1+", V2 = "+v2);
        }
        if (v1.length != v2.length) {
            throw new InvalidDataException("Length of Both Vectors don't match. Length of V1 = "+v1.length+", Length of V2 = "+v2.length);
        }
    }

    /**
     *
     * @param vector vector for which we have to calculate magnitude
     * @return magnitude of the vector given in param
     */
    private Double calculateVectorMagnitude(List<Integer> vector) {
        Integer sumOfSquare = vector.stream().mapToInt(value -> value*value).sum();
        return Math.sqrt(sumOfSquare.doubleValue());
    }

    /**
     *
     * @param book1 vector for users who bought book1
     * @param book2 vector for users who bought book2
     * @return Similarity between two books in a Double data type.
     */
    public Double getSimilarity(Integer[] book1, Integer[] book2) throws InvalidDataException {

        //check if the input vectors are valid
        validateVectors(book1, book2);

        Double dotProduct = 0.0;
        for (int i=0; i<book1.length; i++){
            dotProduct+= book1[i]*book2[i];
        }

        Double denominator = calculateVectorMagnitude(Arrays.asList(book1)) * calculateVectorMagnitude(Arrays.asList(book2));

        if (denominator == 0.0) {
            return 0.0;
        } else {
            return dotProduct/denominator;
        }
    }

}
