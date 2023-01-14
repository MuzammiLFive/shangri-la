import React from "react";
import "./submit-reading.css";
import {post} from "../../common/http";

interface ReadingData {
    date: Date;
    electricityDay: number;
    electricityNight: number;
    gas: number;
}

export class SubmitReading extends React.Component<any, any> {

    today() {
        const today = new Date();
        return today.toISOString().substr(0, 10);
    }

    onUpdateReading() {

        const date = (document.getElementById("date") as HTMLInputElement).value;
        const readingDayElectricity = (document.getElementById("elecDay") as HTMLInputElement).value as unknown as number;
        const readingNightElectricity = (document.getElementById("elecNight") as HTMLInputElement).value as unknown as number;
        const gasReading = (document.getElementById("gas") as HTMLInputElement).value as unknown as number;

        if (!date || !readingDayElectricity || !readingNightElectricity || !gasReading) {
            alert("Reading cannot be empty");
            return;
        }
        const data: ReadingData = {
            date: new Date(date),
            electricityDay: readingDayElectricity,
            electricityNight: readingNightElectricity,
            gas: gasReading
        }
        post("/customer/submit-reading", data)
            .then(res => {
                if (res?.ok) {
                    alert("Readings submitted!");
                }
            });
    }

    render() {
        return (
            <div>
                <div className="sub">submit reading</div>
                <div className="submitReading">
                    <div>Billing Date: <input type="date" id="date" value={this.today()} max={this.today()}></input>
                    </div>
                    <div>Electricity Reading Day: <input id="elecDay" type="number"></input></div>
                    <div>Electricity Reading Night: <input id="elecNight" type="number"></input></div>
                    <div>Gas Reading Night: <input id="gas" type="number"></input></div>
                    <input type="button" onClick={() => this.onUpdateReading()} value="Submit Reading"/>
                </div>
            </div>
        )
    }
}
