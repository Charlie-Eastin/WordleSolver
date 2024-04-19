# <div align="center">Modified Wordle Solver</div>
<div align="center">Charlie Eastin, Jeremy Hulse, Varun Iyer, Saketh Ruddarraju, Chris Worcester</div>

---
## Project Description
Originating as a single-player game, the New York Times Wordle challenges participants to discern a predetermined word composed of uppercase letters within a limited number of attempts. Each guess provides feedback, highlighting correctly positioned letters in green, misplaced ones in yellow, and absent ones in grey. This project extends the Wordle paradigm by introducing novel complexities and avenues for AI-driven solutions.

This project aims to enhance the classic Wordle puzzle game by implementing artificial intelligence-based algorithms for precise and efficient puzzle-solving. Our modified Wordle game allows users to select a randomized sequence of alphabetic letters as the target word, while an AI agent attempts to deduce this word with unlimited guesses. Notably, the AI has the flexibility to submit arbitrary sequences of alphabetic characters as guesses, regardless of their status as valid dictionary words.

---
## Running the Application
Follow the prompts displayed in the console to interact with the Wordle solver.

---
## Task Environment Description
- **Single Agent:** This project operates within a singular agent framework, devoid of interactions with external entities.
- **Sequential:** The agent's decision-making process unfolds iteratively, with prior experiences serving as foundational pillars for subsequent conjectures.
- **Discrete:** The agent's decision-making landscape is discretized, constrained by finite choices dictating the selection of characters for each slot within the word.
- **Known:** The governing principles and variables of the task environment are comprehensively understood by the agent, facilitating informed decision-making.

### Key Modifications
- **Partial Observability:** The task environment introduces dynamics whereby the agent may progressively lose visibility of certain letters within the target word.
- **Stochastic Mutation:** In response to erroneous conjectures, there exists a probabilistic mechanism for letters within the guessed word to undergo mutation.
- **Unrestricted Guessing:** The AI agent is unbound by conventional linguistic constraints, capable of proposing any conceivable sequence of alphabetic characters as potential solutions.
- **Dynamic Environment:** The solution space is not static but rather subject to temporal fluctuations, necessitating a high degree of adaptability from the AI.

## Objectives
1. Develop and implement a refined genetic algorithm tailored to efficiently explore the solution space amidst dynamic environmental shifts.
2. Incorporate partial observability and stochastic mutation mechanisms to fortify the AI agent's problem-solving capabilities.
3. Undertake comparative evaluations with alternative methodologies such as hill climbing and simulated annealing.

## Genetic Algorithm Approach
The decision to employ a modified genetic algorithm stems from its demonstrated efficacy in traversing expansive search spaces while exhibiting remarkable adaptability to fluctuating task environments. Recognized for its capacity to navigate uncertainty and prioritize salient information, the genetic algorithm emerges as a compelling choice for addressing the multifaceted challenges inherent in our modified Wordle game.
