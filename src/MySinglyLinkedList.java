import java.util.Arrays;
import java.util.Objects;

public class MySinglyLinkedList<E> {
    private Node <E> head; // 노드의 첫 부분을 가리키는 포인트
    private Node <E> tail; // 노드의 마지막 부분을 가리키는 포인트
    private int size; // 요소 갯수
    public MySinglyLinkedList() {
        this.head = null; // 원시 타입이 아니라면 null 값 가능임
        this.tail = null;
        this.size = 0;
    }

    // inner static class(노드 클래스)
    private static class Node <E> {
        private E item; // Node에 담을 데이터
        private Node <E> next; // 다음 Node 객체를 가르키는 래퍼런스

        // 생성자
        Node(E item, Node<E> next) {
            this.item = item;
            this.next = next;
        }
    }
    // 앞에서 차례대로 확인 메서드: 내부적으로 정의할 메소드에서 사용할 메소드 정의
    private Node<E> search(int index) { // 반환값이 노드 형태임
        // head(처음 위치) 서부터 차례로 index 까지 검색1
        Node<E> n = head;
        for (int i = 0; i < index; i++) { // 만약 i 가 0 이면 그냥 head 노드를 반환한다

            n = n.next; // next 필드의 값(다음 노드 주소)를 재대입하면서 순차적으로 요소를 탐색
        }
        return n;
    }


    // 노드 추가
    public void addFirst(E value) {

        // 1. 먼저 가장 앞의 요소를 가져옴
        Node<E> first = head;

        // 2. 새 노드 생성 (이때 데이터와 next 포인트를 준다)
        // 원래 가장 앞에 있던 놈이 뒤로 밀리기에 첫번째 놈의 다음 놈이 된다.
        Node<E> newNode = new Node<E>(value, first);

        // 3. 요소가 추가되었으니 size를 늘린다
        size++;

        // 4. 맨앞에 요소가 추가되었으니 head를 업데이트한다. head는 무엇을 바라보고 있는지.
        head = newNode;

        // 5. 만일 최초로 요소가 add 된 것이면 head와 tail이 가리키는 요소는 같게 된다.
        // 그 어떤 노드도 없던 상태라면 head와 tail 모두 null 상태였을 것이다.
        if (first == null) {
            tail = newNode;
        } // 뭐 그렇지 않다면 굳이 tail을 바꿀 필요는 없다.
    }
    public void addLast(E value) {

        // 1. 먼저 가장 뒤의 요소를 가져옴
        Node<E> last = tail;

        // 2. 새 노드 생성 (맨 마지막 요소 추가니까 next 는 null이다)
        Node<E> newNode = new Node<E>(value, null);

        // 3. 요소가 추가되었으니 size를 늘린다
        size++;

        // 4. 맨뒤에 요소가 추가되었으니 tail를 업데이트한다
        tail = newNode;

        if (last == null) {
            // 5. 만일 최초로 요소가 add 된 것이면 head와 tail이 가리키는 요소는 같게 된다.
            head = newNode;
        } else {
            // 6. 최초 추가가 아니라면 last 변수(추가 되기 전 마지막이었던 요소)에서 추가된 새 노드를 가리키도록 업데이트
            last.next = newNode;
        }
    }
    public void add(int index, E value) {

        // 1. 인덱스가 0보다 작거나 size 보다 같거나 클 경우 에러
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        // 2. 추가하려는 index가 0이면 addFirst 호출
        if (index == 0) {
            addFirst(value);
            return;
        }

        // 3. 추가하려는 index가 size - 1와 같으면 addLast 호출
        if (index == size - 1) {
            addLast(value);
            return;
        }

        // 4. 추가하려는 위치의 이전 노드 얻기
        Node<E> prev_node = search(index - 1);

        // 5. 추가하려는 위치의 다음 노드 얻기
        Node<E> next_node = prev_node.next;

        // 6. 새 노드 생성 (바로 다음 노드와 연결)
        Node<E> newNode = new Node<>(value, next_node);

        // 7. size 증가
        size++;

        // 8. 이전 노드를 새 노드와 연결
        prev_node.next = newNode;
    }
    public boolean add(E value) {
        addLast(value);
        return true;
    }
    @Override
    public String toString() {
        // 1. 만일 head 가 null 일 경우 빈 배열
        if(head == null) {
            return "[]";
        }

        // 2. 현재 size만큼 배열 생성
        Object[] array = new Object[size];

        // 3. 노드 next를 순회하면서 배열에 노드값을 저장
        int index = 0;
        Node<E> n = head;
        while (n != null) {
            array[index] = (E) n.item;
            index++;
            n = n.next;
        }

        // 3. 배열을 스트링화하여 반환
        return Arrays.toString(array);
    }


    // 인덱스에 맞는 노드 반환
    public E get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        return search(index).item;
    }


    // 특정 인덱스에 값 넣기
    public void set(int index, E value) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        // 1. search 메소드를 이용해 교체할 노드를 얻는다.
        Node<E> replace_node = search(index);

        // 2. 교체할 노드의 요소를 변경한다.
        replace_node.item = value;
    }


    // 노드 제거
    public E removeFirst() {

        // 1. 만약 삭제할 요소가 아무것도 없으면 에러
        if (head == null) {
            throw new RuntimeException();
        }

        // 2. 삭제될 첫번째 요소의 데이터를 백업
        E returnValue = head.item;

        // 3. 두번째 노드를 임시 저장
        Node<E> first = head.next;

        // 4. 첫번째 노드의 내부 요소를 모두 삭제
        head.next = null;
        head.item = null;

        // 5. head가 다음 노드를 가리키도록 업데이트
        head = first;

        // 6. 요소가 삭제 되었으니 크기 감소
        size--;

        // 7. 만일 리스트의 유일한 값을 삭제해서 빈 리스트가 될 경우, tail도 null 처리
        if (head == null) {
            tail = null;
        }

        // 8. 마지막으로 삭제된 요소를 반환
        return returnValue;
    }
    public E remove() {
        return removeFirst();
    }
    public E remove(int index) {

        // 1. 인덱스가 0보다 작거나 size 보다 크거나 같은 경우 에러 (size와 같을 경우 빈 공간을 가리키는 거니까)
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        // 2. 인덱스가 0이면 removeFirst 메서드 실행하고 리턴
        if (index == 0) {
            return removeFirst();
        }

        // 3. 삭제할 위치의 이전 노드 저장
        Node<E> prev_node = search(index - 1);

        // 4. 삭제할 위치의 노드 저장
        Node<E> del_node = prev_node.next;

        // 5. 삭제할 위치의 다음 노드 저장
        Node<E> next_node = del_node.next;

        // 6. 삭제될 첫번째 요소의 데이터를 백업
        E returnValue = del_node.item;

        // 7. 삭제 노드의 내부 요소를 모두 삭제
        del_node.next = null;
        del_node.item = null;

        // 8. 요소가 삭제 되었으니 크기 감소
        size--;

        // 9. 이전 노드가 다음 노드를 가리키도록 업데이트
        prev_node.next = next_node;

        // 10. 마지막으로 삭제된 요소를 반환
        return returnValue;
    }
    public boolean remove(Object value) {

        // 1. 만약 삭제할 요소가 아무것도 없으면 에러
        if (head == null) {
            throw new RuntimeException();
        }

        // 2. 이전 노드, 삭제 노드, 다음 노드를 저장할 변수 선언
        Node<E> prev_node = null;
        Node<E> del_node = null;
        Node<E> next_node = null;

        // 3. 루프 변수 선언
        Node<E> i = head;

        // 4. 노드의 next를 순회하면서 해당 값을 찾는다.
        while (i != null) {
            if (Objects.equals(i.item, value)) {
                // 노드의 값과 매개변수 값이 같으면 삭제 노드에 요소를 대입하고 break
                del_node = i;
                break;
            }

            // Singly Linked List에는 prev 정보가 없기 때문에 이전 노드에도 요소를 일일히 대입하여야 함
            prev_node = i;


            i = i.next;
        }

        // 5. 만일 찾은 요소가 없다면 리턴
        if (del_node == null) {
            return false;
        }

        // 6. 만약 삭제하려는 노드가 head라면, 첫번째 요소를 삭제하는 것이니 removeFirst()를 사용
        if (del_node == head) {
            removeFirst();
            return true;
        }

        // 7. 다음 노드에 삭제 노드 next의 요소를 대입
        next_node = del_node.next;

        // 8. 삭제 요소 데이터 모두 제거
        del_node.next = null;
        del_node.item = null;

        // 9. 요소가 삭제 되었으니 크기 감소
        size--;

        // 10. 이전 노드가 다음 노드를 참조하도록 업데이트
        prev_node.next = next_node;

        return true;
    }
    public E removeLast() {
        return remove(size - 1);
    }
}