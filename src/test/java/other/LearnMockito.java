// package other;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.DisplayName;
// import org.junit.jupiter.api.Test;
// import org.mockito.ArgumentMatcher;
// import org.mockito.Mockito;

// import java.util.List;

// import static org.mockito.Mockito.*;


// public class LearnMockito {
    
//     private List mockedList;
    
//     @BeforeEach
//     void before() {
//         mockedList = mock(List.class);
//     }
    
//     @Test
//     @DisplayName("Learn mock and verify")
//     void learn_mock_and_verify() {
//         mockedList.add("one");
//         verify(mockedList).add("one");
//     }

//     @Test
//     @DisplayName("Learn when then")
//     void learn_when_then() {
//         when(mockedList.get(0)).thenReturn("first");
//         mockedList.get(0);
//         verify(mockedList).get(0);
//     }
    
//     @Test
//     @DisplayName("Argument matchers")
//     void argument_matchers() {
//         when(mockedList.get(anyInt())).thenReturn("yeah");
//         when(mockedList.contains(argThat(isValid()))).thenReturn(true);
//         when(mockedList.contains(argThat(
//                 (e) -> {return true;}
//         ))).thenReturn(true);
//     }
    
//     @Test
//     @DisplayName("All arguments must be provided")
//     void all_arguments_must_be_provided() {
//         SomeClass someClass = mock(SomeClass.class);
//         someClass.someMethod(1, "", "try changing this argument below");
//         verify(someClass).someMethod(
//                 anyInt(),
//                 anyString(),
//                 eq("try changing this argument below"));
//     }
    
//     @Test
//     @DisplayName("Exact number of invocations")
//     void exact_number_of_invocations() {
//         mockedList.add("one");
//         verify(mockedList).add("one"); /* times(1) is default */
//         verify(mockedList, times(1)).add("one");
//         verify(mockedList, atLeastOnce()).add("one");
//         verify(mockedList, atMostOnce()).add("one");
//         verify(mockedList, never()).add("two");
//     }
    
//     @Test
//     @DisplayName("Stubbing VOID methods with exceptions")
//     void stubbing_void_methods_with_exceptions() {
//         doThrow(new RuntimeException()).when(mockedList).clear();
//         // mockedList.clear();
//     }

//     private ArgumentMatcher <Object> isValid() {
//         return new Vasya();
//     }
    
    
//     class Vasya implements ArgumentMatcher {
        
//         @Override
//         public boolean matches(Object o) {
//             return true;
//         }
//     }
    
    
// }





