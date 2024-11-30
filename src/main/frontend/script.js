const apiBaseUrl = "http://localhost:8080";

async function startGame() {
    try {
        const consoleBox = document.getElementsByClassName("console")[0];
        const buttons = document.getElementsByClassName("startButtons");
        document.getElementById("resetButton").disabled = false;
        for (let button of buttons) {
            button.disabled = true;
        }
        let preOutput = await fetch("http://localhost:8080/start", {method: "GET"});
        let output = await preOutput.text();

        consoleBox.value += "\n> " + output;
        consoleBox.scrollTop = consoleBox.scrollHeight;
    } catch (error) {
        console.error("Error in startGame: ", error);
    }
}

async function continueButton() {
    try {
        let gameStage = await fetch("http://localhost:8080/getGameStage", {method: "GET"});
        const input = document.querySelector(".inputBox");
        const consoleBox = document.querySelector(".console");
        let gameStageText = await gameStage.text();
        console.log(gameStageText);

        if (gameStageText === "getSponsor") {
            fetch("http://localhost:8080/getSponsorInput", {
                method: "POST",
                headers: {
                    "Content-Type": "text/plain"
                },
                body: input.value})
            .then(response => response.text())
            .then(data => {
                consoleBox.value += "\n> " + data;
                consoleBox.scrollTop = consoleBox.scrollHeight;
                input.value = "";
            }).catch(error => {
                console.error("Fetch error:", error);
            });
        } else {
            let preOutput = await fetch("http://localhost:8080/continue", {method: "GET"});
            let output = await preOutput.text();

            consoleBox.value += "\n> " + output;
            consoleBox.scrollTop = consoleBox.scrollHeight;
        }
    } catch (error) {
        console.error("Error in continueButton: ", error);
    }

}