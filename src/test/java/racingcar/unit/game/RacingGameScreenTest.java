package racingcar.unit.game;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import racingcar.game.RacingGameScreen;
import racingcar.game.vo.RacerPosition;
import racingcar.game.vo.RacingCarNamesInput;
import racingcar.game.vo.TotalTurnInput;
import racingcar.game.vo.TurnResult;
import racingcar.io.reader.Reader;
import racingcar.io.writer.Writer;

class RacingGameScreenTest {

    private final MockReader mockReader = new MockReader();
    private final MockWriter mockWriter = new MockWriter();
    private final RacingGameScreen racingGameScreen = new RacingGameScreen(mockReader, mockWriter);

    static class MockReader implements Reader {
        private String input;

        @Override
        public String readLine() {
            return input;
        }

        @Override
        public void close() {
        }

        public void setInput(String input) {
            this.input = input;
        }
    }

    static class MockWriter implements Writer {
        private final ByteArrayOutputStream output = new ByteArrayOutputStream();

        @Override
        public void writeLine(String message) {
            new PrintStream(output).println(message);
        }

        public String getOutput() {
            return output.toString();
        }
    }

    @DisplayName("경주할 자동차 이름 입력 화면을 올바르게 구성하였는지 확인한다.")
    @Test
    void test_InputRacingScreen(){
        //given
        String input = "name1,name2";
        mockReader.setInput(input);
        //when
        RacingCarNamesInput racingCarNamesInput = racingGameScreen.inputRacingCar();
        //then
        String output = mockWriter.getOutput();
        assertThat(output).isEqualTo("경주할 자동차 이름을 입력하세요.(이름은 쉼표(,) 기준으로 구분)\n");
        assertThat(racingCarNamesInput.input()).isEqualTo(input);
    }

    @DisplayName("시도 횟수 입력 화면을 올바르게 구성하였는지 확인한다.")
    @Test
    void test_InputNumberOfTurnsScreen(){
        //given
        String input = "10";
        mockReader.setInput(input);
        //when
        TotalTurnInput totalTurnInput = racingGameScreen.inputTotalTurn();
        //then
        String output = mockWriter.getOutput();
        assertThat(output).isEqualTo("시도할 횟수는 몇 회인가요?\n");
        assertThat(totalTurnInput.input()).isEqualTo(input);
    }

    @DisplayName("실행 결과 출력을 시작하는 화면을 올바르게 구성하였는지 확인한다.")
    @Test
    void test_StartShowGameResultScreen(){
        //given
        //when
        racingGameScreen.startShowGameResult();
        //then
        String output = mockWriter.getOutput();
        assertThat(output).isEqualTo("\n실행 결과\n");
    }


    @DisplayName("최종 우승자 출력 화면을 올바르게 구성하였는지 확인한다.")
    @ParameterizedTest
    @ValueSource(strings = {"name1", "name1,name2", "name1,name2,name3"})
    void test_ShowFinalWinnerScreen(String expected) {
        //given
        List<String> result = Arrays.asList(expected.split(","));
        //when
        racingGameScreen.showFinalWinner(result);
        //then
        String output = mockWriter.getOutput();
        assertThat(output).isEqualTo(String.format("최종 우승자 : %s\n", expected));
    }

    @DisplayName("실행 결과 화면을 올바르게 구성하였는지 확인한다.")
    @Test
    void test_ShowTurnResultScreen() {
        //given
        List<RacerPosition> result = new ArrayList<>();
        result.add(new RacerPosition("name1", 1));
        result.add(new RacerPosition("name2", 2));

        TurnResult turnResult = new TurnResult(result);
        //when
        racingGameScreen.showTurnResult(turnResult);
        //then
        String output = mockWriter.getOutput();
        assertThat(output).isEqualTo("""
                name1 : -
                name2 : --
                                
                """);
    }
}