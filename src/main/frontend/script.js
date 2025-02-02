const apiBaseUrl = "http://localhost:8080";
let lastOutput = "";

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
        updateTable()
    } catch (error) {
        console.error("Error in startGame: ", error);
    }
}

async function A1Start() {
    try {
        const consoleBox = document.getElementsByClassName("console")[0];
        const buttons = document.getElementsByClassName("startButtons");
        document.getElementById("resetButton").disabled = false;
        for (let button of buttons) {
            button.disabled = true;
        }
        let preOutput = await fetch("http://localhost:8080/startA1", {method: "GET"});
        let output = await preOutput.text();

        consoleBox.value += "\n> " + output;
        consoleBox.scrollTop = consoleBox.scrollHeight;
        updateTable()
    } catch (error) {
        console.error("Error in startGame: ", error);
    }
}

async function second_Scenario() {
    try {
        const consoleBox = document.getElementsByClassName("console")[0];
        const buttons = document.getElementsByClassName("startButtons");
        document.getElementById("resetButton").disabled = false;
        for (let button of buttons) {
            button.disabled = true;
        }
        let preOutput = await fetch("http://localhost:8080/second_Scenario", {method: "GET"});
        let output = await preOutput.text();

        consoleBox.value += "\n> " + output;
        consoleBox.scrollTop = consoleBox.scrollHeight;
        updateTable()
    } catch (error) {
        console.error("Error in startGame: ", error);
    }
}

async function third_Scenario() {
    try {
        const consoleBox = document.getElementsByClassName("console")[0];
        const buttons = document.getElementsByClassName("startButtons");
        document.getElementById("resetButton").disabled = false;
        for (let button of buttons) {
            button.disabled = true;
        }
        let preOutput = await fetch("http://localhost:8080/third_Scenario", {method: "GET"});
        let output = await preOutput.text();

        consoleBox.value += "\n> " + output;
        consoleBox.scrollTop = consoleBox.scrollHeight;
        updateTable()
    } catch (error) {
        console.error("Error in startGame: ", error);
    }
}

async function fourth_Scenario() {
    try {
        const consoleBox = document.getElementsByClassName("console")[0];
        const buttons = document.getElementsByClassName("startButtons");
        document.getElementById("resetButton").disabled = false;
        for (let button of buttons) {
            button.disabled = true;
        }
        let preOutput = await fetch("http://localhost:8080/fourth_Scenario", {method: "GET"});
        let output = await preOutput.text();

        consoleBox.value += "\n> " + output;
        consoleBox.scrollTop = consoleBox.scrollHeight;
        updateTable()
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

        checkOutput();
        updateTable();

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
        } else if (gameStageText === "questBuild") {
            fetch("http://localhost:8080/questBuild", {
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
        } else if (gameStageText === "chooseParticipant") {
            fetch("http://localhost:8080/chooseParticipant", {
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
                updateTable();
            }).catch(error => {
                console.error("Fetch error:", error);
            });
        } else if (gameStageText === "trimHand") {
            const response = await fetch("http://localhost:8080/trimHand", {
                  method: "POST"
            })

            if (!response.ok) {
                throw new Error(`Error: ${response.status}`);
            }

            const data = await response.text();

            checkOutput();
        } else if (gameStageText === "resolveAttack") {
            const response = await fetch("http://localhost:8080/resolveAttack", {
                  method: "POST"
            })

            if (!response.ok) {
                throw new Error(`Error: ${response.status}`);
            }
            const data = await response.text();
            checkOutput();

            //consoleBox.value += "\n> " + data;
            //console.log(data);
            consoleBox.scrollTop = consoleBox.scrollHeight;
        } else if (gameStageText === "getInput") {
            if (input.value === "") return;

            const response = await fetch("http://localhost:8080/setInput", {
                  method: "POST",
                  headers: {
                    "Content-Type": "text/plain"
                  },
                  body: input.value}
            )

            if (!response.ok) {
                throw new Error(`Error: ${response.status}`);
            }
            const data = await response.text();
            input.value = "";

            await sleep(200);
            checkOutput();
            consoleBox.scrollTop = consoleBox.scrollHeight;
            updateTable();
        } else {
            let preOutput = await fetch("http://localhost:8080/continue", {method: "GET"});
            let output = await preOutput.text();

            consoleBox.value += "\n> " + output;
            consoleBox.scrollTop = consoleBox.scrollHeight;
            updateTable();
        }
    } catch (error) {
        console.error("Error in continueButton: ", error);
    }

}

function checkOutput() {
    const consoleBox = document.querySelector(".console");

    fetch("http://localhost:8080/getOutput", {
        method: "GET"
    })
    .then(response => response.text())
    .then(data => {
        if (lastOutput === data) {
            console.log(true);
            return true;
        }
        lastOutput = data;
        consoleBox.value = consoleBox.value + lastOutput;
        consoleBox.scrollTop = consoleBox.scrollHeight;
        console.log(false);
        return false;
    }).catch(error => {
        console.error("Fetch error:", error);
    });
}

function sleep(ms) {
    return new Promise((resolve) => setTimeout(resolve, ms));
}

async function resetButton() {
    await fetch("http://localhost:8080/reset", {method: "POST"});
    document.getElementById("resetButton").disabled = true;
    const buttons = document.getElementsByClassName("startButtons");
    for (let button of buttons) {
        button.disabled = false;
    }

    lastOutput = "";
    const input = document.querySelector(".inputBox");
    const consoleBox = document.querySelector(".console");

    input.value = "";
    consoleBox.value = "";

    document.querySelector("#p1").value = "";
    document.querySelector("#p2").value = "";
    document.querySelector("#p3").value = "";
    document.querySelector("#p4").value = "";

    document.querySelector("#p1Shields").textContent = "";
    document.querySelector("#p1Cards").textContent = "";

    document.querySelector("#p2Shields").textContent = "";
    document.querySelector("#p2Cards").textContent = "";

    document.querySelector("#p3Shields").textContent = "";
    document.querySelector("#p3Cards").textContent = "";

    document.querySelector("#p4Shields").textContent = "";
    document.querySelector("#p4Cards").textContent = "";
}

async function updateTable() {
    fetch("http://localhost:8080/getModel", {
        method: "GET",
        headers: { 'Content-Type': 'application/json'
    }})
    .then(response => response.json())
    .then(data => {
        if (data === null) return;
        for (let player of data.players) {
            document.querySelector("." + player.name).children[1].textContent = player.shields;
            document.querySelector("." + player.name).children[2].textContent = player.cards.length;

            document.getElementById("p" + player.name.charAt(1)).value = "";
            for(let card of player.cards) {
                document.getElementById("p" + player.name.charAt(1)).value += card.card + ", ";
            }
        }
    })
}

setInterval(updateTable, 100);
