import java.util.Arrays;

public class CellHeap{

    private int size;
    private Cells[] heap;

    public CellHeap(){
        size = 0;
        heap = new Cells[2];
    }

    public void buildHeap()
    {
        for (int k = size/2; k > 0; k--)
        {
            percolatingDown(k);
        }
    }

    private void percolatingDown(int k)
    {
        Cells tmp = heap[k];
        int child;

        for(; 2*k <= size; k = child)
        {
            child = 2*k;

            if(child != size &&
                    heap[child].compareTo(heap[child + 1]) > 0) child++;

            if(tmp.compareTo(heap[child]) > 0)  heap[k] = heap[child];
            else
                break;
        }
        heap[k] = tmp;
    }

    public Cells deleteMin() throws RuntimeException
    {
        if (size == 0) throw new RuntimeException();
        Cells min = heap[1];
        heap[1] = heap[size--];
        percolatingDown(1);
        return min;
    }

    public void insert(Cells x)
    {
        if(size == heap.length - 1) doubleSize();

        //Insert a new item to the end of the array
        int pos = ++size;

        //Percolate up
        for(; pos > 1 && x.compareTo(heap[pos/2]) < 0; pos = pos/2 )
            heap[pos] = heap[pos/2];

        heap[pos] = x;
    }

    private void doubleSize()
    {
        Cells [] old = heap;
        heap = new Cells[heap.length * 2];
        System.arraycopy(old, 1, heap, 1, size);
    }

    public boolean contains(Cells c) {
        if(c.compareTo(heap[1]) < 0){
            return false;
        }else{
            return Arrays.stream(heap).anyMatch(hc -> c == hc);
        }
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size <= 0;
    }
}
