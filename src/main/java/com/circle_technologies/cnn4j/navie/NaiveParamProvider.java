package com.circle_technologies.cnn4j.navie;

import com.circle_technologies.cnn4j.predictive.network.Params;
import com.circle_technologies.cnn4j.predictive.network.SimpleParams;
import com.circle_technologies.cnn4j.predictive.provider.ParamProvider;

/**
 * Created by Sellm on 31.08.2016.
 */
public class NaiveParamProvider implements ParamProvider {
    @Override
    public Params provideParams() {
        String[] inputParams = new String[]{"initial", "second_hand_date", "gw_price_netto", "mileage", "retail_price",
                "0A1", "0A2", "0AC", "0AD", "0AE", "0AQ", "0AR", "0B1", "0B2", "0BA", "0BC", "0BD", "0BE", "0BF", "0BK", "0BT", "0C0", "0EC", "0EG", "0EJ",
                "0EM", "0EN", "0ES", "0EX", "0F0", "0F3", "0F5", "0FA", "0HC", "0IC", "0K0", "0K2", "0K3", "0LE", "0LF", "0LJ", "0LK", "0LL", "0LN", "0LS"
                , "0LT", "0LU", "0LW", "0LY", "0N1", "0N4", "0NA", "0NB", "0P0", "0P1", "0P2", "0P9", "0SS", "0ST", "0SV", "0SZ", "0TC", "0TD", "0U0", "0U2"
                , "0VA", "0VB", "0VC", "0VD", "0VF", "0VG", "0VH", "0VQ", "0VS", "0VV", "0VW", "0W1", "0W2", "0XA", "0XC", "0Y1", "0Y2", "0Y3", "0Y5", "0YB"
                , "0YC", "0YD", "0YE", "0YF", "0YG", "0YH", "0YJ", "1A5", "1A7", "1AP", "1AS", "1AT", "1AZ", "1D0", "1D2", "1E0", "1E1", "1EB", "1EX", "1G6"
                , "1G8", "1G9", "1JA", "1JC", "1KD", "1KE", "1KJ", "1KS", "1KW", "1KY", "1LC", "1LG", "1LJ", "1M5", "1MR", "1MU", "1N3", "1N7", "1PA", "1PD"
                , "1PE", "1PF", "1QA", "1QC", "1S1", "1S2", "1S3", "1SA", "1SB", "1SJ", "1SK", "1T1", "1T2", "1T3", "1U8", "1W0", "1X0", "1X1", "1XW", "1XX"
                , "1Z0", "1Z1", "1Z2", "1Z5", "1Z6", "1Z7", "1ZA", "1ZD", "1ZE", "1ZF", "1ZK", "20A", "20B", "2D0", "2G5", "2G7", "2H0", "2H2", "2H6", "2H7"
                , "2JB", "2JD", "2JP", "2JU", "2JW", "2KA", "2KQ", "2KR", "2KS", "2LC", "2LD", "2PF", "2PG", "2PK", "2PV", "2UA", "2UB", "2UC", "2UF", "2UG"
                , "2V1", "2V5", "2WA", "2WB", "2WC", "2WD", "2WK", "2WS", "2WT", "2XM", "2XP", "2Y0", "2Y1", "2Y2", "2YK", "2Z0", "2Z1", "2Z7", "2Z8", "2ZQ"
                , "2ZS", "3B0", "3B4", "3B7", "3C0", "3C7", "3CA", "3FA", "3FB", "3FE", "3FN", "3FT", "3G0", "3G1", "3G2", "3G4", "3H0", "3H9", "3J1", "3K0"
                , "3K3", "3KC", "3L1", "3L3", "3L5", "3M1", "3M3", "3NN", "3NU", "3NZ", "3PB", "3PQ", "3Q5", "3Q6", "3S0", "3S1", "3S2", "3SB", "3SG", "3SX"
                , "3T2", "3TB", "3TG", "3TX", "3X0", "3X2", "3Y0", "3Y3", "3Y4", "3Y6", "4A0", "4A3", "4A4", "4F2", "4GF", "4GH", "4GP", "4GQ", "4H0", "4H3"
                , "4H5", "4I2", "4I3", "4K4", "4KC", "4L2", "4L6", "4L7", "4M0", "4P0", "4P1", "4P4", "4P5", "4R4", "4UE", "4UF", "4UH", "4UP", "4W0", "4X1"
                , "4X3", "4X4", "4ZB", "4ZD", "4ZE", "4ZL", "4ZM", "4ZP", "5A4", "5A8", "5C0", "5D1", "5D4", "5J0", "5J2", "5J3", "5J4", "5K0", "5K4", "5K5"
                , "5K8", "5MA", "5MC", "5MD", "5ME", "5MP", "5MY", "5NC", "5RU", "5SJ", "5SL", "5TD", "5TG", "5TL", "5TZ", "5ZF", "6C0", "6E0", "6E3", "6F0"
                , "6F1", "6F2", "6F3", "6F6", "6F7", "6FA", "6FC", "6FM", "6FT", "6K0", "6K2", "6K3", "6K4", "6NJ", "6NQ", "6NZ", "6Q3", "6Q5", "6Q6", "6SJ"
                , "6SS", "6TS", "6U0", "6V1", "6V5", "6W6", "6W7", "6XC", "6XD", "6XE", "6XJ", "6XK", "7A0", "7A2", "7AA", "7AL", "7B0", "7B2", "7BH", "7BL"
                , "7BP", "7DH", "7DK", "7DM", "7DY", "7E0", "7E1", "7E6", "7EA", "7ES", "7F9", "7G0", "7G9", "7HA", "7HE", "7HF", "7K0", "7K1", "7K6", "7K9"
                , "7L3", "7L6", "7L7", "7LJ", "7M0", "7M2", "7M3", "7M5", "7M7", "7M8", "7M9", "7MG", "7MJ", "7MM", "7P0", "7P1", "7PE", "7Q0", "7Q2", "7Q9"
                , "7QA", "7QB", "7QT", "7S0", "7S1", "7TK", "7TM", "7UF", "7UG", "7UH", "7W0", "7W1", "7X0", "7X1", "7X2", "7X5", "7Y0", "7Y1", "7Y4", "7Y5"
                , "8AY", "8BD", "8BP", "8G0", "8G1", "8G2", "8GU", "8GW", "8ID", "8IG", "8IM", "8IT", "8K1", "8K4", "8M0", "8M1", "8N1", "8N4", "8N6", "8N7"
                , "8Q1", "8Q3", "8Q5", "8RE", "8RM", "8RY", "8SA", "8SK", "8T0", "8T2", "8T3", "8T5", "8TB", "8UD", "8UL", "8UM", "8W1", "8WA", "8WB", "8WM"
                , "8X0", "8X1", "8Y1", "8Y3", "8Z4", "8Z5", "8Z6", "9AA", "9AK", "9AP", "9JA", "9JD", "9JE", "9JH", "9K0", "9K1", "9M0", "9M1", "9M9", "9P1"
                , "9P3", "9P8", "9Q0", "9Q3", "9Q7", "9S4", "9S5", "9S6", "9T0", "9T1", "9TB", "9TC", "9U1", "9VD", "9VS", "9W0", "9Y0", "9Y1", "9ZC", "9ZE"
                , "9ZF", "9ZK", "9ZU", "9ZX", "A00", "A51", "A52", "A53", "A8F", "A9D", "A9E", "A9F", "AQ0", "AQ1", "AQ2", "AQ4", "AQ6", "AQ8", "AV0", "AV1"
                , "B01", "B02", "B03", "B06", "B07", "B08", "B09", "B0A", "B0N", "B10", "B12", "B13", "B14", "B15", "B19", "B1P", "B20", "B31", "B36", "B3D"
                , "B45", "C00", "C01", "C02", "C03", "C04", "C05", "C06", "C07", "C08", "C09", "C0F", "C0J", "C0T", "C0U", "C0X", "C0Z", "C10", "C11", "C12"
                , "C13", "C14", "C15", "C1B", "C1I", "C1J", "C2F", "C2L", "C2Q", "C2T", "C32", "C33", "C35", "C3L", "C3R", "C3Z", "C4F", "C4N", "C5I", "C5S"
                , "C6H", "C7D", "C7M", "C8C", "C8F", "C8N", "C9X", "CB7", "CB8", "CC1", "CF5", "CF7", "CH2", "CJ1", "CJ2", "CL3", "CL7", "CP4", "CS0", "CS1"
                , "CZ0", "D07", "D2L", "D33", "D36", "D38", "D3Q", "D4X", "D67", "D91", "D93", "DB0", "DF4", "DF5", "DF6", "DG6", "DK5", "DK7", "DN0", "DN4"
                , "DP2", "DQ0", "DS4", "E0A", "E2M", "E4V", "EA0", "EA1", "EA2", "EA3", "EA4", "EA5", "EA8", "EA9", "EF0", "EF1", "EL0", "EL1", "EL3", "ER1"
                , "ER6", "ES0", "ES3", "EV0", "EV3", "EW0", "F0A", "F0X", "F30", "F37", "F38", "F40", "F41", "F5F", "F74", "F75", "F78", "FA0", "FA4", "FA5"
                , "FA6", "FC0", "FC1", "FM0", "FM1", "FN0", "FN1", "FQ0", "FQ1", "G01", "G02", "G03", "G04", "G05", "G06", "G07", "G0C", "G0J", "G0K", "G0L"
                , "G0R", "G12", "G17", "G1A", "G1C", "G1D", "G20", "G21", "G22", "G23", "G24", "G25", "G26", "G27", "G28", "G29", "G31", "G32", "G33", "G34"
                , "G35", "G36", "G37", "G38", "G41", "G42", "G43", "G44", "G46", "G47", "G51", "G78", "GP0", "H0R", "H0V", "H13", "H1Y", "H27", "H4J", "H4S"
                , "H6G", "H6L", "H6U", "H8V", "HJ0", "HJ4", "HM4", "HN9", "HX5", "HX8", "HY6", "I4C", "I4K", "I4S", "I7X", "I8A", "I8B", "I8D", "I8F", "I8G"
                , "IX1", "IX2", "J0L", "J0N", "J0R", "J0S", "J0T", "J1D", "J1L", "J2D", "K8G", "K8K", "K8R", "K8S", "KA0", "KA2", "KH6", "KK1", "KK3", "L01"
                , "L02", "L03", "L04", "L05", "L06", "L07", "L08", "L0L", "L0R", "L10", "L11", "L12", "L13", "L14", "L16", "L19", "L25", "L26", "L28", "L29"
                , "L30", "L31", "L32", "L33", "L36", "L37", "L38", "L39", "L42", "L47", "L48", "L49", "L55", "L58", "L59", "L60", "L62", "L63", "L74", "L77"
                , "L81", "L82", "L83", "L84", "L85", "L86", "L88", "L89", "L90", "L91", "N0S", "N1A", "N1Q", "N1R", "N1T", "N1U", "N2C", "N2R", "N3M", "N3Q"
                , "N3U", "N4U", "N5B", "N5G", "N5W", "N5X", "N7K", "N7U", "N7V", "NT0", "NT1", "NY0", "NY2", "P3D", "PB2", "PC2", "PCB", "PCE", "PCG", "PDA"
                , "PDH", "PDN", "PDS", "PEG", "PEP", "PEQ", "PG3", "PGK", "PI1", "PI3", "PID", "PKB", "PKC", "PLE", "PLF", "PNB", "PNQ", "PNU", "PNV", "PNX"
                , "PO1", "PO2", "PQA", "PQD", "PS1", "PS4", "PS5", "PS6", "PS7", "PSW", "PU7", "PU8", "PX2", "PX4", "PXA", "PYA", "PYC", "PYI", "PYO", "PYZ"
                , "PZ5", "PZ7", "PZ8", "PZ9", "PZA", "Q1A", "Q4H", "Q4P", "QA0", "QE0", "QE1", "QE3", "QE4", "QE5", "QG1", "QH0", "QH1", "QI0", "QI3", "QI4"
                , "QI6", "QJ0", "QJ1", "QK0", "QK1", "QQ0", "QQ4", "QQ5", "QV0", "QV3", "QV9", "RCM", "RSM", "RSX", "S0C", "S0L", "S0R", "S0S", "S0T", "S1R"
                , "S1X", "S3G", "S3R", "S99", "S9T", "S9X", "SA6", "SA7", "SA8", "SA9", "SB5", "SL5", "SV3", "SY1", "TA2", "TA8", "TB9", "TD6", "TE6", "TF3"
                , "TH8", "TJ1", "TK8", "TL1", "TL4", "TM5", "TP1", "TR1", "TT6", "TU0", "TW0", "U1A", "U1B", "U2A", "U5A", "U5B", "UA0", "UA1", "UA2", "UA3"
                , "UA4", "UA5", "UA6", "UA7", "UB2", "UB5", "UC7", "UE3", "UE7", "UF0", "UF1", "UF2", "UF7", "UG0", "UG1", "UG3", "UG4", "UH1", "UH2", "UK1"
                , "V0A", "V17", "V18", "VC0", "VC1", "VF0", "VF1", "VG6", "VL0", "VL1", "VT0", "VW1", "W30", "W3C", "WAJ", "WAK", "WAS", "WAT", "WAV", "WAX"
                , "WB1", "WB5", "WB6", "WBI", "WBL", "WCE", "WCN", "WCT", "WDA", "WDT", "WFD", "WKG", "WKI", "WKO", "WLA", "WLB", "WLC", "WNT", "WQC", "WQH"
                , "WQK", "WQS", "WQV", "WS9", "WSP", "WTK", "Y10", "Y16", "Y34", "Y46", "Y8A", "Y8F", "Y8G", "Y8H", "Y8T", "Y8U", "Y8X", "YB7", "YBB", "YBJ"
                , "YBM", "YBS", "YBV", "YBY", "YBZ", "YCA", "YCB", "YCC", "YCD", "YCE", "YCF", "YCG", "YCH", "YCI", "YCJ", "YCK", "YCL", "YCM", "YCN", "YCO"
                , "YCP", "YCQ", "YCR", "YCS", "YCT", "YCU", "YCW", "YCZ", "YEA", "YEB", "YEC", "YED", "YEF", "YEG", "YEH", "YEI", "YEV", "YEW", "YG3", "YG4"
                , "YG5", "YG6", "YG7", "YG9", "YGA", "YGB", "YGC", "YGD", "YGE", "YGF", "YGG", "YGJ", "YGL", "YGM", "YGP", "YGQ", "YGR", "YGS", "YGT", "YGV"
                , "YGW", "YGX", "YGY", "YJA", "YJB", "YJJ", "YJP", "YO1", "YO2", "YO3", "YO4", "YO5", "YO6", "YO7", "YO8", "YO9", "YOA", "YOB", "YOC", "YRB"
                , "YSI", "YSZ", "YTA", "YTF", "YTZ", "YUC", "YUG", "YVG", "YYY", "Z30", "Z35"};
        String[] outputParams = new String[]{"second_hand_price"};
        return new SimpleParams(inputParams, outputParams);
    }
}
