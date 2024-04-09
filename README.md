# dangdang-BE

</br>

- git fetch는 로컬 Git에게 원격 저장소에서 최신 메타데이터 정보를 확인
- fetch는 원격 저장소에 변경사항이 있는지 확인만 하고, 변경된 데이터를 로컬 Git에 실제로 가져오지는 않음
- git pull은 원격 저장소에서 변경된 메타데이터 정보를 확인하고, 최신 데이터를 복사하여 로컬 Git에 가져옴

`$ git fetch`

`$ git pull origin develop`

----------

- develop 브랜치 있는지 확인

`$ git branch`

</br>

- develop 브랜치에 있는 상태에서 작업 브랜치 생성
- develop 브랜치로 변경

`$ git switch develop`

</br>

- develop 브랜치로 변경됐는지 확인

`$ git branch`

----------

- 작업 브랜치 생성
- 브랜치 이름은 “이슈라벨/#이슈번호”

`$ git branch setting/#1`

</br>

- 작업 브랜치 생성됐는지 확인

`$ git branch`

</br>

- 생성된 브랜치로 변경

`$ git switch setting/#1`

</br>

- 작업 브랜치로 변경됐는지 확인

`$ git branch`

---------

## 로컬에서 작업 수행

----------

### 파일 올리기
#### add → commit → push 순서

- add

`$ git add .`

</br>

- 상태 확인하기

`$ git status`

</br>

> **필요한 경우 사용**
>
> - add한 파일 모두 취소
> 
> `$ git rm --cached -r .`
>
> - 특정 파일만 add 취소
> 
> `$ git rm --cached [파일]`
>

</br>

- commit
- 커밋 메세지 작성하고 커밋하기
- 커밋 메세지에 이슈 번호 적어주기(#이슈번호)
- git commit -m "commit message"

`$ git commit -m “setting: 프로젝트 초기 설정 (#1)”`

</br>

- **작업한 브랜치 push**
- git push —set-upstream origin [작업한브랜치]
  
`$ git push —set-upstream origin setting/#1`






