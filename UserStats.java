public class UserStats {
    private int attempts;
    private int correct;

    public void recordResult(boolean isCorrect) {
        attempts++;
        if (isCorrect) {
            correct++;
        }
    }   

    public int getAttempts() {
        return attempts;
    }
    public int getCorrect() {
        return correct;
    }
    public void addattempts(int num) {
        attempts += num;
    }
    public void addcorrect(int num) {
        correct += num;
    }
    public double getAccuracy() {
        if (attempts == 0) {
            return 0.0;
        }
        return (double) correct / attempts * 100;
    }   
    @Override
    public String toString() {
        return correct + "/" + attempts + " (" + String.format("%.2f", getAccuracy()) + "%)";
    }
    
}
