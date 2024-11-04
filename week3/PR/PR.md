# 로또

## 고민했던 지점

### 1. 검증은 누구의 책임인가?

> 디스코드에서도 활발한 주제였던 '검증'입니다.
>
> 1,2주차에서는 외부 검증 객체를 빼서 작성해보았고, 이번에는 도메인에 작성해보았습니다.
>
> 검증 객체를 빼야할 때는 도메인의 책임이 아니거나, 도메인 로직이 복잡해서 그것들 보다는 중요도가 많이 떨어질 때 라고 생각했습니다.
>
> 또한 도메인과 검증을 함께 둬야할 때는 검증 로직 자체가 도메인과 많이 밀집해 있을 때라고 생각했습니다.

**오랜 고민 끝에 저는 이 두 방법을 혼용하기로 했습니다!**

- 도메인과 많이 닿아있는 검증들은 도메인이 직접 처리합니다.
  - 로또 번호는 6개로만 구성된다.
  - 로또 번호는 1~45의 숫자로만 구성된다.
  - 구매금액은 1000원 단위만 된다.
  - ...
- 도메인이 아닌 외부로 빼도 될 검증은 외부 검증 객체가 담당합니다.
  - 파라미터로 전달된 값들의 null을 검증합니다.
- IO와 관련된 검증은 IO 계층이 직접 담당합니다.
  - 숫자값을 받아야 하는데, 숫자 형식이 맞는지 검증합니다.
  - 숫자리스트값을 받아야 하는데, 숫자 형식이 맞는지 검증합니다.
  - ...

이렇게 분리하여 도메인은 스스로를 단단하게 검증할 수 있고, 자신의 세부 구현 사항을 검증을 위해 외부로 노출시킬 필요가 없습니다.

또한 간단하고 반복되는 검증들은 외부 검증 객체를 통해 처리하기 때문에, 도메인의 응집도가 상승됩니다.

### 2. 재시도는 어떻게 처리할 것인가?

> 저는 개인적으로 이번 주차의 메인 주제는 '단위 테스트'와 '재시도'였다고 생각합니다.
>
> 저는 특히 재시도에 신경을 많이 썼는데, 그 고민의 과정을 나눠보고자 합니다!
>
> 고민 1
> 제가 재시도 기능이 어려웠던 이유는, 재시도 로직은 중요하지 않은 외부 관심사라고 생각했기 때문입니다.
>
> 사용자에게 값을 입력받고, 검증하고, 로또 돌리는 것들은 비즈니스 로직이라고 판단했지만,
>
> 예외가 발생하면 이를 출력하고 다시 시도하도록 한다...? 이런 짜투리 로직이 비즈니스 로직과 같이 있는게 싫었습니다.
>
> 어떻게 하면 이 둘을 완벽하게 분리해낼까 라는 고민을 많이 하게 되었습니다!
>
> 고민 2
> 또한 재시도를 람다식을 통해서 할까, 자바 리플렉션을 활용해서 할까도 고민을 많이 했습니다.
>
> 람다식은 쉽고 간단하지만, 코드가 조금 못생겨지는 경향이 있고,
> 리플렉션은 조금 어렵지만, 코드가 깔끔해집니다.

**고민 1과 고민 2는 프록시패턴을 통해 한번에 해결되었습니다!**

프록시 패턴은 진짜 객체를 바로 부르지 않고 해당 객체를 구성하는 프록시 객체를 두고, 프록시 객체가 어떠한 일을 하도록 하는 것입니다.

```java
public class WinningLottoControllerRetryProxy implements WinningLottoController {

    private final WinningLottoController target;
    private final RetryHandler retryHandler;

    public WinningLottoControllerRetryProxy(WinningLottoController target, RetryHandler retryHandler) {
        this.target = target;
        this.retryHandler = retryHandler;
    }

    @Override
    public Lotto readWinningNumbers() {
        return retryHandler.tryUntilSuccess(target::readWinningNumbers);
    }

    @Override
    public WinningLotto createWinningLotto(Lotto lotto) {
        return retryHandler.tryUntilSuccess(() -> target.createWinningLotto(lotto));
    }
}
```

이 방식을 통해서 재시도 로직을 비즈니스 로직과 완전히 분리할 수 있었습니다.

더해서, 이 프록시 객체는 구성하지 않으면 그냥 재시도가 없는 서비스로 수정될 수 있습니다.

리플렉션은 (제가 아는 한) 이런 유연한 구성이 불가능하다고 알고 있어서, 람다식으로 결정하게 되었습니다.

### 3. 도메인들의 행복한 세상을 만들어보자.

> 말이 조금 웃긴데, 저는 이번 과제가 도메인주도 설계를 마음껏 써먹을 수 있는 과제라고 생각했습니다.
>
> 각 도메인이 응집성 높고, 비즈니스 로직을 완벽하게 처리하도록 구성하고자 노력했습니다.
>
> 그러다보니 기준이 애매해서, 저 스스로 몇가지의 기준을 세우고 이를 지키려고 했습니다.

1. 도메인은 다른 도메인 외에 그 어떠한 의존성도 가지지 않는다.
2. 도메인은 스스로를 검증하고, 적절한 상태를 유지할 수 있도록 한다.
3. 도메인은 비즈니스에 필요한 인터페이스를 제공하며, 그 외의 기능은 전부 감춘다.
4. 컨트롤러가 얇게 구성되어야 도메인이 더 많은 일을 할 수 있기 때문에, 컨트롤러를 최대한 얇게 구성한다.

## 주요 클래스 다이어그램

![클래스 다이어그램](img.png)

## 디렉토리 구조

```markdown
├── Application
├── aop
│ └── RetryHandler
├── config
│ ├── LottoApplicationConfig
│ ├── aop
│ │ └── RetryHandlerConfig
│ ├── controller
│ │ ├── moneyController
│ │ │ ├── DefaultMoneyControllerConfig
│ │ │ └── MoneyControllerConfig
│ │ └── winningLottoController
│ │ ├── DefaultWinningLottoControllerConfig
│ │ └── WinningLottoControllerConfig
│ ├── io
│ │ ├── InputHandlerConfig
│ │ ├── OutputHandlerConfig
│ │ ├── reader
│ │ │ ├── DefaultReaderConfig
│ │ │ └── ReaderConfig
│ │ └── writer
│ │ ├── DefaultWriterConfig
│ │ └── WriterConfig
│ └── numberPicker
│ ├── DefaultNumberPickerConfig
│ └── NumberPickerConfig
├── controller
│ ├── LottoApplicationFacade
│ ├── lottoController
│ │ └── LottoController
│ ├── lottoStaticsController
│ │ └── LottoStaticsController
│ ├── moneyController
│ │ ├── DefaultMoneyController
│ │ ├── MoneyController
│ │ └── MoneyControllerRetryProxy
│ └── winningLottoController
│ ├── DefaultWinningLottoController
│ ├── WinningLottoController
│ └── WinningLottoControllerRetryProxy
├── domain
│ ├── Lotto
│ ├── LottoPrize
│ ├── LottoStatics
│ ├── Money
│ ├── Number
│ ├── WinningLotto
│ ├── numberPicker
│ │ ├── NumberPicker
│ │ └── RandomNumberPicker
│ └── validator
│ └── ParamsValidator
├── dto
│ └── ...
├── exception
│ └── ...
└── io ├── OutputHandler ├── OutputParser ├── inputHandler │ ├── DefaultInputHandler │ └── InputHandler ├── reader │ ├──
MissionUtilsReader │ └── Reader └── writer ├── SystemWriter └── Writer
```