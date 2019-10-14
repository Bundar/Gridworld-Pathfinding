import java.util.Arrays;

class CellHeap{

    private int size;
    private Cells[] heap;

    CellHeap(){
        size = 0;
        heap = new Cells[2];
    }

    void buildHeap()
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

    Cells deleteMin() throws RuntimeException
    {
        if (size == 0) throw new RuntimeException();
        Cells min = heap[1];
        heap[1] = heap[size--];
        percolatingDown(1);
        return min;
    }

    void insert(Cells x)
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

    boolean isNotContained(Cells c) {
        if(c.compareTo(heap[1]) < 0){
            return true;
        }else{
            return Arrays.stream(heap).noneMatch(hc -> c == hc);
        }
    }

    boolean notEmpty() {
        return size > 0;
    }
}
