# TimeMate

## Development model
**TimeMate** is a system for time reporting and is developed in Java.
This project is divided into **four** phases, following the waterfall model. A phase is not officially started until the previous one is completed.

At the end of every phase several documents must be produced and to complete the current phase, all of the documents required must pass an informal review, followed by a formal review.

After every phase the following documents must be produced:

### Phase 1
- [SDP - Software Development Plan](P1-Baseline-Specification/PUSS214200_SDP)
- [SRS - Software Requirements Specification](P1-Baseline-Specification/PUSS214201_SRS)
- [SVVS - Software Verification and Validation Specification](P1-Baseline-Specification/PUSS214202_SVVS)

### Phase 2
- [SVVI - Software Verification and Validation Instruction](P2-Baseline-Desgin-Test/PUSS214203_SVVI) 
- [STLDD - Software Top Level Design Document](P2-Baseline-Desgin-Test/PUSS214204_STLDD)

### Phase 3
- [SDDD - Software Detailed Design Document](P3-Baseline-Informal/PUSS214205_SDDD)

### Phase 4
- [SVVR - Software Verification and Validation Report](P4-Baseline-Product/PUSS214206_SVVR) 
- [SSD - System Specification Document](P4-Baseline-Product/PUSS214207_SSD) 
- [PFR - Project Final Report](P4-Baseline-Product/PUSS214208_PFR)

---

## Workflow
The repository is divided into **three** seperate branches.
All main work is done in the *development* branch.

Once documents pass **informal** review, they are merged into the *review* branch.

Once the documents pass **formal** review, they are merged into the *master* branch.

![Workflow](images/workflow.png)
*Illustrates the branching workflow in the project.*

---

## Git terminal cheat sheet
- Clone repo: `git clone URL`     
- Update current branch: `git pull REMOTE BRANCH`
- Push current branch: `git push -u REMOTE BRANCH`
- Config user information: `git config --global user.name "NAME" && git config --global user.email "EMAIL"`
- Show status: `git status`
- Switch branch: `git checkout BRANCHNAME` 
- Create branch: `git checkout -b BRANCHNAME`
- Show latest commits: `git log`
- Show latest commits as graoh: `git log --graph`

## Final dates
- Project hand-in Monday 22/3 12:00
- Accept Meeting Thrusday 25/3 11:00
